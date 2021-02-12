package markdown

private const val tableData = """
"""

fun main() {
    toMarkDownTable(tableData.lines(), "\t")
}

fun toMarkDownTable(lines: List<String>, separator: String) {
    val rows = lines.map { it.trim() }.filter { it.isNotEmpty() }.map { it.split(separator) }
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
