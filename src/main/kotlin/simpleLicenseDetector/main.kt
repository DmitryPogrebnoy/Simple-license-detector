package simpleLicenseDetector

fun main() {
    println("Enter the full path to the root project folder for license scanning")
    val pathToFolder: String = readLine()!!
    println("Finding licenses...")
    try {
        val result = LicenseFinder.findAllLicense(pathToFolder)
        println(result.formatResult())
    } catch (e: Exception) {
        println(e.message)
    }
}