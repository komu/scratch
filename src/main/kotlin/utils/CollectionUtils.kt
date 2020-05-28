package utils

fun <T> Iterable<T>.duplicates(): Set<T> =
    asSequence().duplicates()

fun <T> Sequence<T>.duplicates(): Set<T> {
    val seenValues = HashSet<T>()
    val duplicates = LinkedHashSet<T>()

    for (value in this)
        if (!seenValues.add(value))
            duplicates += value

    return duplicates
}

fun <T> List<T>.withPrevious(): List<Pair<T?, T>> =
    indices.map { i -> Pair(getOrNull(i - 1), this[i]) }

inline fun <T : Comparable<T>> Iterable<ClosedRange<T>>.mergeRanges(predicate: (ClosedRange<T>, ClosedRange<T>) -> Boolean): List<ClosedRange<T>> =
    merge(predicate) { a, b -> a.start..b.endInclusive }

inline fun <T> Iterable<T>.merge(predicate: (T, T) -> Boolean, merge: (T, T) -> T): List<T> {
    val result = mutableListOf<T>()

    for (range in this) {
        val preceding = result.lastOrNull()

        if (preceding != null && predicate(preceding, range)) {
            result[result.lastIndex] = merge(preceding, range)
        } else {
            result += range
        }
    }

    return result
}
