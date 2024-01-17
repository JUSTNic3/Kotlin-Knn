import kotlinx.coroutines.*
import kotlin.random.Random

fun main() {
    val data = loadCsvData("database/imdb_movies.csv")

    println("Loaded ${data.size} movie records. Do you want to proceed with all records? (yes/no): ")
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
                    println("Film found: $filmData")
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
                println("Terminating search.")
                break
            }
        }
    }
}

// Function to simulate a loading animation
fun loadingAnimation(): String {
    val animations = listOf("-", "\\", "|", "/", "-", "\\", "|", "/") 
    return animations[Random.nextInt(animations.size)]
}

// Function to search for a film by name
fun searchFilm(filmName: String, data: List<Map<String, String>>): Map<String, String>? {
    return data.firstOrNull { it["names"]?.equals(filmName, ignoreCase = true) == true }
}
