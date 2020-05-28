package utils

import java.time.Duration
import java.time.LocalDateTime

fun <T> Iterable<T>.sumByDuration(f: (T) -> Duration): Duration {
    var sum = Duration.ZERO
    for (x in this)
        sum += f(x)
    return sum
}

@JvmName("localDateTimeRangeToDuration")
fun ClosedRange<LocalDateTime>.toDuration(): Duration =
    Duration.between(start, endInclusive)
