package utils

import java.util.*

private val random = Random()

fun <T> List<T>.randomElement(): T {
    require(isNotEmpty()) { "empty list" }
    return this[random.nextInt(size)]
}
