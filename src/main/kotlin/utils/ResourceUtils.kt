package utils

import java.io.IOException
import java.io.InputStream

private val classLoader = object {}.javaClass.classLoader

fun openResource(path: String): InputStream =
    classLoader.getResourceAsStream(path) ?: throw IOException("Could not open resource: '$path'")

fun readResourceLines(path: String): List<String> =
    openResource(path).use { it.reader().readLines() }
