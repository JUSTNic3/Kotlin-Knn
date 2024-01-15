// Ensure that CsvLoader.kt and DataProcessor.kt are in the same package,
// or import CsvLoader.kt if it's in a different package.

fun processData(csvData: List<Map<String, String>>) {
    val highRatedMovies = csvData.filter { it["score"]?.toDoubleOrNull() ?: 0.0 > 80.0 }
        .map { "${it["names"]} (${it["date_x"]}): Score ${it["score"]}" }
    highRatedMovies.forEach { println(it) }
}

fun main() {
    val csvData = loadCsvData("database/imdb_movies.csv") // This line calls the function from CsvLoader.kt
    processData(csvData)
}
