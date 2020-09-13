package simpleLicenseDetector

import simpleLicenseDetector.license.License

/**
 * Enumeration of all supported licenses
 *
 * Each license provides basic information about yourself and implementation of methods
 * for its determination in the license file and the source file.
 */

data class LicenseFinderResult(
    val rootLicense: HashSet<License> = HashSet(),
    val filesLicense: HashSet<License> = HashSet()
) {
    fun formatResult(): String {
        val outputStringBuilder = StringBuilder("\n\n")
        if (rootLicense.isEmpty()) {
            outputStringBuilder.append("This project does not have a main license.\n")
        } else {
            outputStringBuilder.append("This project has the following main licenses:")
            for (license in rootLicense) {
                outputStringBuilder.append("\n * ${license.fullName}")
            }
        }

        outputStringBuilder.append("\n\n")

        if (filesLicense.isEmpty()) {
            outputStringBuilder.append("Project files not licensed.\n")
        } else {
            outputStringBuilder.append("The project has files under the following licenses:")
            for (license in filesLicense) {
                outputStringBuilder.append("\n * ${license.fullName}")
            }
        }

        return outputStringBuilder.toString()
    }
}