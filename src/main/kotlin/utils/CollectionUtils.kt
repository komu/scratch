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
