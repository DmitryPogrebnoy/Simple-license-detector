package simpleLicenseDetector

import simpleLicenseDetector.license.License
import simpleLicenseDetector.license.LicenseType
import java.io.File

object LicenseFinder {

    /**
     * Regular expression to determine if a file is a license file
     */

    private val licenseFileNameRegex: Regex

    init {
        val pattern: StringBuilder = java.lang.StringBuilder("LICENSE(\\..*)?")
        for (license in LicenseType.values()) {
            pattern.append("|${license.fullName}|${license.shortName}")
        }
        licenseFileNameRegex = pattern.toString().toRegex(RegexOption.IGNORE_CASE)
    }

    /**
     * This function searches for licenses in the project.
     * First, we go through all the files in the root directory
     * and look for files with a license.
     * Next, we iterate over all non-root project files and try
     * to find licenses in them using regular expressions.
     */

    fun findAllLicense(pathToRootFolder: String): LicenseFinderResult {
        val rootFile = File(pathToRootFolder)
        if (!rootFile.exists()) {
            throw RuntimeException("The root project directory does not exists!")
        }
        if (!rootFile.isDirectory) {
            throw RuntimeException("The root project directory does not a directory!")
        }

        val rootDirListFiles = rootFile.listFiles()
            ?: throw RuntimeException("Error getting the list of files in the root directory.")

        val rootProjectLicenses: HashSet<License> = HashSet()
        val filesLicenses: HashSet<License> = HashSet()

        val dirInRootDir: ArrayList<File> = ArrayList()

        for (file in rootDirListFiles) {
            if (file.isFile) {
                if (licenseFileNameRegex.matches(file.name)) {
                    println("License file: ${file.absolutePath}")
                    val license = getLicenseFromLicenseFile(file)
                    if (license != null) {
                        rootProjectLicenses.add(license)
                    }
                } else {
                    println("File: ${file.absolutePath}")
                    val license = getLicenseFromSourceFile(file)
                    if (license != null) {
                        filesLicenses.add(license)
                    }
                }
            } else if (file.isDirectory) {
                dirInRootDir.add(file)
            }
        }

        for (dir in dirInRootDir) {
            filesLicenses.addAll(processFolder(dir))
        }

        return LicenseFinderResult(rootProjectLicenses, filesLicenses)
    }


    private fun processFolder(dir: File): HashSet<License> {
        val filesLicenses: HashSet<License> = HashSet()

        for (file in dir.walk()) {
            if (file.isFile) {
                val license = getLicenseFromFile(file)
                if (license != null) {
                    filesLicenses.add(license)
                }
            }
        }

        return filesLicenses
    }

    private fun getLicenseFromFile(file: File): License? {
        return if (licenseFileNameRegex.matches(file.name)) {
            println("License file: ${file.absolutePath}")
            getLicenseFromLicenseFile(file)
        } else {
            println("File: ${file.absolutePath}")
            getLicenseFromSourceFile(file)
        }
    }

    private fun getLicenseFromLicenseFile(file: File): License? {
        for (license in LicenseType.values()) {
            if (license.licenseFileHasLicense(file.readText())) {
                return license
            }
        }
        return null
    }

    private fun getLicenseFromSourceFile(file: File): License? {
        for (license in LicenseType.values()) {
            if (license.sourceFileHasLicense(file.readText())) {
                return license
            }
        }
        return null
    }

}