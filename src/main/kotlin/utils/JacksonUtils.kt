package utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

inline fun <reified T : Any> ObjectMapper.readResource(path: String): T =
    openResource(path).use { readValue(it) }
