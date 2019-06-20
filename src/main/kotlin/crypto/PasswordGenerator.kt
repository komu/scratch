package crypto

import java.security.SecureRandom

fun main(args: Array<String>) {
    val random = SecureRandom()
    val alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    println(buildString {
        repeat (22) {
            append(alphabet[random.nextInt(alphabet.length)])
        }
    })
}