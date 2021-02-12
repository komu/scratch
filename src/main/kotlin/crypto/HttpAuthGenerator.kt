package crypto

import java.util.*

/**
 * Encodes login and password to the format used by HTTP Basic authorization.
 */
fun main() {
    val login = "login"
    val password = "password"

    println(Base64.getEncoder().encodeToString("$login:$password".toByteArray()))
}
