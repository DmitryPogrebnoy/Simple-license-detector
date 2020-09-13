package simpleLicenseDetector.license

enum class LicenseType : License {
    ApacheLicenseV2 {
        override val fullName: String = "Apache License 2.0"
        override val shortName: String = "Apache-2.0"
        override val nameRegex: Regex = Regex(
            ".*Apache( (License\\s|License, Version |License\\s*Version )?|-)(2|2\\.0)(\\s?|,?).*",
            RegexOption.IGNORE_CASE
        )
        override val fullTextLicenseRegex: Regex = nameRegex
        private val headerLicenseRegex: Regex = Regex(
            this::class.java.classLoader.getResource(
                "ApacheLicenseV2HeaderRegex"
            )!!.readText(), RegexOption.IGNORE_CASE
        )

        override fun sourceFileHasLicense(fileText: String): Boolean {
            return nameRegex.find(fileText) != null || headerLicenseRegex.find(fileText) != null
        }
    },
    MIT {
        override val fullName: String = "MIT License"
        override val shortName: String = "MIT"
        override val nameRegex: Regex = Regex(".*MIT License.*", RegexOption.IGNORE_CASE)
        override val fullTextLicenseRegex: Regex = Regex(
            this::class.java.classLoader.getResource(
                "MITLicenseFullTextRegex"
            )!!.readText(), RegexOption.IGNORE_CASE
        )
        private val headerLicenseRegex: Regex = Regex(
            this::class.java.classLoader.getResource(
                "MITLicenseHeaderRegex"
            )!!.readText(), RegexOption.IGNORE_CASE
        )

        override fun sourceFileHasLicense(fileText: String): Boolean {
            return nameRegex.find(fileText) != null || headerLicenseRegex.find(fileText) != null
        }
    },
    GPLV3 {
        override val fullName: String = "GNU General Public License v3.0 only"
        override val shortName: String = "GPL-3.0-only"
        override val nameRegex: Regex = Regex(
            ".*(gnu general public license(\\n*|\\s*|, )?Version (3|3.0)|GPL( |-| - )(3|3.0)).*",
            RegexOption.IGNORE_CASE
        )
        override val fullTextLicenseRegex: Regex = nameRegex
        private val headerLicenseRegex: Regex = Regex(
            this::class.java.classLoader.getResource(
                "GPLV3HeaderRegex"
            )!!.readText(), RegexOption.IGNORE_CASE
        )

        override fun sourceFileHasLicense(fileText: String): Boolean {
            return nameRegex.find(fileText) != null || headerLicenseRegex.find(fileText) != null
        }
    },
    BSD3Clause {
        override val fullName: String = "BSD 3-Clause \"New\" or \"Revised\" License"
        override val shortName: String = "BSD-3-Clause"
        override val nameRegex: Regex = Regex("(BSD 3-Clause|BSD 3-Clause \"New\" or \"Revised\" License)")
        override val fullTextLicenseRegex: Regex = Regex(
            this::class.java.classLoader.getResource(
                "BSD3ClauseFullTextRegex"
            )!!.readText(), RegexOption.IGNORE_CASE
        )
        private val headerLicenseRegex: Regex = Regex(
            this::class.java.classLoader.getResource(
                "BSD3ClauseHeaderRegex"
            )!!.readText(), RegexOption.IGNORE_CASE
        )

        override fun sourceFileHasLicense(fileText: String): Boolean {
            return nameRegex.find(fileText) != null || headerLicenseRegex.find(fileText) != null
        }
    },
    LGPLV3 {
        override val fullName: String = "GNU Lesser General Public License v3.0 only"
        override val shortName: String = "LGPL-3.0-only"
        override val nameRegex: Regex = Regex(
            ".*(gnu lesser general public license(\\n*|\\s*|, )?Version (3|3.0)|LGPL( |-| - )(3|3.0)).*",
            RegexOption.IGNORE_CASE
        )
        override val fullTextLicenseRegex: Regex = nameRegex
    };

    protected abstract val nameRegex: Regex
    protected abstract val fullTextLicenseRegex: Regex

    override fun sourceFileHasLicense(fileText: String): Boolean {
        return nameRegex.find(fileText) != null
    }

    override fun licenseFileHasLicense(licenseFileText: String): Boolean {
        return nameRegex.find(licenseFileText) != null || fullTextLicenseRegex.find(licenseFileText) != null
    }
}