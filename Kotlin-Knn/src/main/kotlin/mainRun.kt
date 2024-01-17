import kotlin.system.exitProcess
/****************************************************
* _____   _____ _    _            _____     ____  *
*|_   _| | ____| |  | |     /\   |  __ \   / __ \ *
*  | |   |  _| | |  | |    /  \  | |__) | | |  | |*
*  | |   | |___| |__| |   / /\ \ |  ___/  | |  | |*
* _| |_  |  ___|  __  |  / ____ \| |      | |__| |*
*|_____| |_|   |_|  |_| /_/    \_\_|       \____/ *
*                                                 *
*****************************************************/


fun main() {
    var continueRunning = true

    while (continueRunning) {
        println("Main Menu:")
        println("1. Find out top Countrys and Languages for movies ")
        println("2. Get detail description of a movie from a database")
        println("3. Find out how much did top 10 movies earn")
        println("4. Run Data Handler Function (DON'T!)")
        println("5. Find your next movie to watch")
        println("6. Exit")
        println("Enter your choice (1-6):")

        val choice = readLine()?.toIntOrNull()

        when (choice) {
            1 -> runDataOOPFunction()
            2 -> runDataParalel()
            3 -> runDataAnalysis()
            4 -> runDataHandler()
            5 -> runDataKNN()
            6 -> continueRunning = false
            else -> println("Invalid choice. Please enter a number between 1 and 6.")
        }

        if (continueRunning) {
            println("Do you want to continue? (yes/no)")
            val continueResponse = readLine()
            if (continueResponse?.toLowerCase() != "yes") {
                continueRunning = false
            }
        }
    }

    println("Exiting program. Goodbye!")
}

// Placeholder functions. Replace with actual functions from your files.
fun runDataOOPFunction() {
    println("Running...")
    dataOOP()
}

fun runDataParalel() {
    println("Running...")
    paralel()
}

fun runDataAnalysis() {
    println("Running...")
    analysis()
}

fun runDataKNN() {
    println("Running...")
    KNN()
}

fun runDataHandler() {
    println("NO!!")
    val emptyList = listOf<Int>()
    val element = emptyList[0]
    println("Element: $element")
}



// Add similar placeholder functions for the other options.

