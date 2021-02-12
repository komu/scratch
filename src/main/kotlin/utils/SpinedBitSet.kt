package utils

/**
 * A BitSet that grows by allocating new buffers into a spine instead of
 * reallocating and copying existing buffer.
 */
class SpinedBitSet {

    private val spine = mutableListOf<LongArray>()

    operator fun get(bitIndex: Int): Boolean {
        if (bitIndex < 0) throw IndexOutOfBoundsException("$bitIndex")

        return if (bitIndex < capacity) {
            val (words, wordIndex) = splitIndex(bitIndex)

            words[wordIndex] and (1L shl bitIndex) != 0L
        } else
            false
    }

    operator fun set(index: Int, bit: Boolean) {
        if (bit)
            set(index)
        else
            clear(index)
    }

    private fun set(bitIndex: Int) {
        while (bitIndex >= capacity)
            spine += LongArray(BUFFER_SIZE)

        val (words, wordIndex) = splitIndex(bitIndex)

        words[wordIndex] = words[wordIndex] or (1L shl bitIndex)
    }

    private fun clear(bitIndex: Int) {
        if (bitIndex > capacity)
            return // attempting to set to 0 something that we implicitly consider 0

        val (words, wordIndex) = splitIndex(bitIndex)
        words[wordIndex] = words[wordIndex] and (1L shl bitIndex).inv()
    }

    private val capacity: Int
        get() = spine.size * BITS_IN_BUFFER

    private fun splitIndex(bitIndex: Int): Pair<LongArray, Int> {
        val index = bitIndex shr ADDRESS_BITS_PER_WORD

        return Pair(spine[index / BUFFER_SIZE], index % BUFFER_SIZE)
    }

    fun iterator() = (0 until 4).asSequence().map { this[it] }.iterator()


    companion object {

        /** Size of individual buffers in the spine */
        private const val BUFFER_SIZE = 64

        private const val BITS_IN_BUFFER = BUFFER_SIZE * Long.SIZE_BITS
        private const val ADDRESS_BITS_PER_WORD = 6
    }
}
