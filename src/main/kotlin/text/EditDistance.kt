package text

import utils.readResourceLines

fun levenshtein(lhs: String, rhs: String): Int {
    var cost = Array(lhs.length) { it }
    var newCost = Array(lhs.length) { 0 }

    for (i in 1 until rhs.length) {
        newCost[0] = i

        for (j in 1 until lhs.length) {
            val match = if (lhs[j - 1] == rhs[i - 1]) 0 else 1

            val costReplace = cost[j - 1] + match
            val costInsert = cost[j] + 1
            val costDelete = newCost[j - 1] + 1

            newCost[j] = minOf(costInsert, costDelete, costReplace)
        }

        val swap = cost
        cost = newCost
        newCost = swap
    }

    return cost[lhs.length - 1]
}

fun List<String>.categorize(distance: Int): Map<String, List<String>> {
    val unprocessed = toMutableList()

    val result = mutableMapOf<String, List<String>>()

    while (unprocessed.isNotEmpty()) {
        val first = unprocessed.removeAt(0).trim()

        if (first.length < 6) {
            result[first] = emptyList()
            continue
        }

        val aliases = unprocessed.drop(1).filter { levenshtein(first.trim(), it.trim()) <= distance }
//        if (aliases.isNotEmpty())
            result[first] = aliases

        unprocessed -= aliases
    }

    return result
}
