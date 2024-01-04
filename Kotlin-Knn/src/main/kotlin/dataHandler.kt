import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

fun main() {
    // Specify the relative path to your CSV file
    val relativePath = "database/imdb_movies.csv"

    try {
        // Read the CSV file using ClassLoader.getResourceAsStream
        val inputStream = object {}.javaClass.classLoader.getResourceAsStream(relativePath)

        if (inputStream != null) {
            val rows: List<List<String>> = csvReader().readAll(inputStream)

            // Print the rows
            println(rows)
        } else {
            println("File not found: $relativePath")
        }
    } catch (e: Exception) {
        println("Error reading the file: $e")
    }
}