package simpleLicenseDetector.license

interface License {
    val fullName: String
    val shortName: String

    fun sourceFileHasLicense(fileText: String): Boolean
    fun licenseFileHasLicense(licenseFileText: String): Boolean
}