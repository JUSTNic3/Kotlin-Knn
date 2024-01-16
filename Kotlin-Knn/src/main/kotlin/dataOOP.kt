// Simplified data class
data class Movie(
    val crew: String,
    val country: String,
    val originalLanguage: String
)
val movies: List<Movie> = loadCsvData("database/imdb_movies.csv").mapNotNull { row ->
    val crew = row["crew"]
    val country = row["country"]
    val originalLanguage = row["orig_lang"]

    if (crew != null && country != null && originalLanguage != null) {
        return@mapNotNull Movie(crew, country, originalLanguage)
    } else {
        null
    }
}
fun main() {
    val movies: List<Movie> = loadCsvData("database/imdb_movies.csv").mapNotNull { row ->

        val crew = row["crew"]
        val country = row["country"]
        val originalLanguage = row["orig_lang"]

        if (crew != null && country != null && originalLanguage != null) {
            return@mapNotNull Movie(crew, country, originalLanguage)
        } else {
            null
        }
    }

    //val actorFrequency = movies.groupBy { it.crew }.mapValues { it.value.size }
    val countryFrequency = movies.groupBy { it.country }.mapValues { it.value.size }
    val languageFrequency = movies.groupBy { it.originalLanguage }.mapValues { it.value.size }

    /*val actorBuckets = actorFrequency.entries.groupBy {
        when(it.value) {
            in 1..9 -> "1-9 movies"
            in 10..99 -> "10-99 movies"
            in 100..999 -> "100-999 movies"
            else -> "1000+ movies"
        }
    }.mapValues { it.value.sumBy { it.value } }*/

    println("\nCountry Frequency:")
    printAsciiChart(countryFrequency)

    println("\nLanguage Frequency:")
    printAsciiChart(languageFrequency)
}

fun printAsciiChart(frequency: Map<String, Int>) {
    val colors = arrayOf("\u001B[31m", "\u001B[32m", "\u001B[33m")
    var index = 0

    frequency.forEach { (key, value) ->
        val bar = "█".repeat(value)
        println("\u001B[34m$key\u001B[0m: $bar") // blue text
        val color = colors[index % colors.size]
        print(color)
        index ++
    }

}

