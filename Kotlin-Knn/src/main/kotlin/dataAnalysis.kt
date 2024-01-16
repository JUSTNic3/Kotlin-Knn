fun processData(csvData: List<Map<String, String>>) {
    // High Rated Movies
    val highRatedMovies = csvData.filter { (it["score"]?.toDoubleOrNull() ?: 0.0) > 50.0 }
        .map { "${it["names"]} (${it["date_x"]}): ${ANSI_BLUE}Score ${it["score"]}/100${ANSI_RESET}" }.
        take(10)

    // Most Profitable Movies
    val mostProfitableMovies = csvData.mapNotNull { row ->
        val revenue = row["revenue"]?.toDoubleOrNull() ?: 0.0
        val budget = row["budget_x"]?.toDoubleOrNull() ?: 0.0

        if (budget > 0.0) {
            val profit = revenue - budget
            Pair(row["names"] ?: "${ANSI_RED}Unknown", String.format("%.1f Billion", profit / 1_000_000_000))
        } else {
            null
        }
    }.sortedByDescending { it.second }
        .take(10)

    println("\n\n${ANSI_GREEN}High Rated Movies:${ANSI_RESET}")
    highRatedMovies.forEach { println("  $it") }

    println("\n\n${ANSI_GREEN}Most Profitable Movies:${ANSI_RESET}")
    mostProfitableMovies.forEach {
        println("  ${it.first}: ${ANSI_YELLOW}Profit ${it.second}${ANSI_RESET}")
    }
}

//colors
const val ANSI_RESET = "\u001B[0m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"

fun main() {
    val csvData = loadCsvData("database/imdb_movies.csv")
    processData(csvData)
}
