package crypto

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Simple utility for generating and verifying BCrypt hashes.
 */
fun main(args: Array<String>) {
    val password = "foo"
    val hash = "\$2a\$10\$j5HIFXW9wt.Xi42s6W5faetLLHGsep2uR9GJZmUmipxxZpDhCBELi"

    val encoder = BCryptPasswordEncoder()
    println("hash: " + encoder.encode(password))
    println("password matches hash: " + encoder.matches(password, hash))
}
