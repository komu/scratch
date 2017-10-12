package utils

import java.io.File
import java.nio.file.Path

fun Path.countLines(): Int =
    toFile().countLines()

fun File.countLines(): Int =
    useLines { it.count() }
