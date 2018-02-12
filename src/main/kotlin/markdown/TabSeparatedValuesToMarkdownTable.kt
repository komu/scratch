package markdown

private const val tableData = """
    <paste your data here>
"""

fun main(args: Array<String>) {
    val rows = tableData.lines().map { it.trim() }.filter { it.isNotEmpty() }.map { it.split("\t") }
    val columns = rows.first()
    val data = rows.drop(1)
    val transposedData = columns.indices.map { index -> data.map { it[index] } }

    println(columns.toMarkdownTableRow())
    println(columns.indices.map { guessColumnType(transposedData[it]).markdownHeader }.toMarkdownTableRow())

    for (row in data)
        println(row.toMarkdownTableRow())
}

enum class ColumnType(val markdownHeader: String) {
    DEFAULT("----"),
    NUMERIC("---:")
}

private fun guessColumnType(values: List<String>) =
    if (values.all { it.isBlank() || it.isNumeric() } )
        ColumnType.NUMERIC
    else
        ColumnType.DEFAULT

private fun String.isNumeric() = matches(Regex("""\d+(\.\d+)?"""))

private fun List<String>.toMarkdownTableRow() =
    joinToString(" | ", prefix = "| ", postfix = " |")
