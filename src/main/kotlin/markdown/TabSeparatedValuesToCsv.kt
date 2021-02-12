package markdown

import java.io.File

fun main() {
    val rows = File("table.txt").readLines().map { it.trim() }.filter { it.isNotEmpty() }.map { it.split("\t") }
    val columns = rows.first()
    val data = rows.drop(1)

    val output = buildString {
        append(columns.joinToString(";")).append("\n")

        for (row in data)
            append(row.joinToString(";")).append("\n")
    }

    File("output.csv").writeText(output)
}

