package utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.random.Random

internal class SpinedBitSetTest {

    @Test
    fun `basic operations`() {
        val set = SpinedBitSet()

        val bigIndex = 42425525
        assertEquals(false, set[bigIndex - 1])
        assertEquals(false, set[bigIndex])
        assertEquals(false, set[bigIndex + 1])

        set[bigIndex] = false

        assertEquals(false, set[bigIndex - 1])
        assertEquals(false, set[bigIndex])
        assertEquals(false, set[bigIndex + 1])

        set[bigIndex] = true

        assertEquals(false, set[bigIndex - 1])
        assertEquals(true, set[bigIndex])
        assertEquals(false, set[bigIndex + 1])

        set[bigIndex] = false
        assertEquals(false, set[bigIndex - 1])
        assertEquals(false, set[bigIndex])
        assertEquals(false, set[bigIndex + 1])
    }

    @Test
    fun `compare against bitset`() {
        val random = Random(42)
        val ss = SpinedBitSet()
        val bs = BitSet()

        var maxIndex = 0
        repeat(1_000_000) {
            val index = random.nextInt(500_000)
            val bit = random.nextBoolean()

            bs.set(index, bit)
            ss[index] = bit

            maxIndex = maxOf(maxIndex, index)
        }

        for (i in 0 until maxIndex) {
            assertEquals(bs[i], ss[i], "$i")
        }
    }
}
