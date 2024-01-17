import kotlinx.coroutines.*
import kotlin.random.Random

fun paralel() {
    val data = loadCsvData("database/imdb_movies.csv")

    println("Loaded ${data.size} movie records. Do you want to proceed with all records? (yes/no): ") //just a ch.
    val proceed = readLine()!!
    if (proceed.lowercase() != "yes") {
        println("Terminating program.")
        return
    }

    runBlocking {
        while (true) {
            println("Enter the name of the film:")
            val filmName = readLine()!!

            val job = async {
                print("Searching for '$filmName' [")
                val filmData = searchFilm(filmName, data)

                for (i in 1..20) { //delay
                    delay(150)
                    print("=")
                }
                println("]")

                if (filmData != null) {
                    printFilmData(filmData)
                } else {
                    println("Film not found.")
                }
            }

            //animation
            while (!job.isCompleted) {
                delay(100)
                print("\b${loadingAnimation()}")  // animation
            }
            println("\b")

            // repeat
            println("Do you want to search for another film? (yes/no)")
            val continueSearch = readLine()!!.lowercase()
            if (continueSearch != "yes") {
                println("Chao!")
                break
            }
        }
    }
}

fun loadingAnimation(): String {
    val animations = listOf("-", "\\", "|", "/", "-", "\\", "|", "/")
    return animations[Random.nextInt(animations.size)]
}

fun searchFilm(filmName: String, data: List<Map<String, String>>): Map<String, String>? {
    return data.firstOrNull { it["names"]?.equals(filmName, ignoreCase = true) == true }
}

fun printFilmData(filmData: Map<String, String>) {
    println("\nFilm found:")
    filmData.forEach { (key, value) ->
        val color = when (key) {
            "names", "orig_title", "crew" -> "\u001B[33m" // Y
            "date_x", "score", "status" -> "\u001B[32m" // B
            "genre", "overview", "orig_lang", "budget_x", "revenue", "country"  -> "\u001B[34m" // B
            else -> "\u001B[0m" // R
        }
        println("$color$key\u001B[0m: $value")
    }
}
