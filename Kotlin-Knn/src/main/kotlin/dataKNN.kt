import kotlin.math.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

data class KNNMovie(
    val name: String,
    val genre: String,
    val crew: String,
    val language: String
)

class KNNAlgorithm(
    private val movies: List<KNNMovie>,
    private val k: Int = 5
) {
    fun findNearestNeighbors(input: KNNMovie): List<KNNMovie> {
        // Compute similarities
        val similarities = movies.map { movie -> Pair(movie, computeKNNSimilarity(movie, input)) }

        return similarities
            .sortedByDescending { it.second }
            .take(k)
            .map { it.first }
    }

    private fun computeKNNSimilarity(movie1: KNNMovie, movie2: KNNMovie): Double {
        val genreWeight = 0.2 // Adjust
        val crewWeight = 0.3
        val languageWeight = 0.4

        val genreSimilarity = jaccardIndex(movie1.genre.toGenreSet(), movie2.genre.toGenreSet())
        val crewSimilarity = jaccardIndex(movie1.crew.toCrewSet(), movie2.crew.toCrewSet())
        val languageMatch = if (movie1.language == movie2.language) 1.0 else 0.0

        return (genreSimilarity * genreWeight) + (crewSimilarity * crewWeight) + (languageMatch * languageWeight)
    }


    private fun String.toGenreSet(): Set<String> = this.split(", ").map { it.trim() }.toSet()
    private fun String.toCrewSet(): Set<String> = this.split(", ").map { it.trim() }.toSet()

    private fun jaccardIndex(set1: Set<String>, set2: Set<String>): Double {
        val intersection = set1.intersect(set2).size.toDouble()
        val union = set1.union(set2).size.toDouble()
        return if (union == 0.0) 0.0 else intersection / union
    }
}

fun KNN() {
    val movies = loadKNNCsvData("database/imdb_movies.csv")

    println("Enter your favorite movie genre (Drama, Action):")
    val favoriteGenre = readLine()!!

    println("Enter your favorite crew member (director, actor):")
    val favoriteCrew = readLine()!!

    println("Enter your favorite language (English, Spanish):")
    val favoriteLanguage = readLine()!!

    val knnAlgorithm = KNNAlgorithm(movies, k = 10)
    val preferredMovie = KNNMovie("",favoriteGenre, favoriteCrew, favoriteLanguage)

    val recommendations = knnAlgorithm.findNearestNeighbors(preferredMovie)

    println("\u001B[32mRecommended movies:\u001B[0m") // Green color for header
    recommendations.forEach { movie ->
        println("\u001B[35mName:\u001B[0m ${movie.name}")
        println("\u001B[36mGenre:\u001B[0m ${movie.genre}")
        println("\u001B[33mCrew:\u001B[0m ${movie.crew}")
        println("\u001B[34mLanguage:\u001B[0m ${movie.language}\n")
    }
}

fun loadKNNCsvData(relativePath: String): List<KNNMovie> {
    val inputStream = object {}.javaClass.classLoader.getResourceAsStream(relativePath)
        ?: throw IllegalArgumentException("File not found at $relativePath")

    val knnMovies = mutableListOf<KNNMovie>()

    csvReader().open(inputStream) {
        readAllWithHeaderAsSequence().forEach { row ->
            val name = row["names"] ?: ""
            val genre = row["genre"] ?: ""
            val crew = row["crew"] ?: ""
            val language = row["orig_lang"] ?: ""

            knnMovies.add(KNNMovie(name, genre, crew, language))
        }
    }

    return knnMovies
}
