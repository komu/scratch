package plots

import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geom_histogram
import jetbrains.letsPlot.ggplot
import jetbrains.letsPlot.label.ggtitle
import java.util.*

fun main() {
    // Plot example

    val rand = Random()
    val data = mapOf<String, Any>(
        "x" to List(500) { rand.nextGaussian() } + List(500) { rand.nextGaussian() + 1.0 },
        "c" to List(500) { "A" } + List(500) { "B" }
    )

    val geom = geom_histogram(alpha = 0.3, size = 0.0) {
        x = "x"; fill = "c"
    }
    val p = ggplot(data) + geom + ggtitle("The normal distribution")

    ggsave(p, "normal-distribution.png")
}
