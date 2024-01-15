import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

fun loadCsvData(relativePath: String): List<Map<String, String>> {
    val inputStream = object {}.javaClass.classLoader.getResourceAsStream(relativePath)
        ?: throw IllegalArgumentException("File not found at $relativePath")

    val csvData = mutableListOf<Map<String, String>>()

    csvReader().open(inputStream) {
        readAllWithHeaderAsSequence().forEach { row ->
            csvData.add(row)
        }
    }

    return csvData
}
