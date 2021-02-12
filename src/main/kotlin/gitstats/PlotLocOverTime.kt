package gitstats

import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geom_line
import jetbrains.letsPlot.label.ggtitle
import jetbrains.letsPlot.lets_plot
import jetbrains.letsPlot.scale.scale_x_datetime
import jetbrains.letsPlot.scale.scale_y_continuous
import org.eclipse.jgit.lib.*
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.TreeWalk
import java.io.File
import java.time.Duration
import java.time.Instant
import kotlin.system.measureTimeMillis

fun main() {
    dumpRepoStats("/Users/komu/src/evident/raudikko/.git", "Raudikko", "raudikko-stats.png")
    dumpRepoStats("/Users/komu/src/evident/dalesbred/.git", "Dalesbred", "dalesbred-stats.png")
    dumpRepoStats("/Users/komu/src/evident/finnpilot/pilotweb/.git", "Pilotweb", "pilotweb-stats.png")
}

private fun dumpRepoStats(repo: String, title: String, file: String) {
    val ms = measureTimeMillis {
        val data = RepoAnalyzer(repo).analyze()

        val geom = geom_line {
            x = "times"
            y = "lines"
        }

        val p = lets_plot(data) +
                geom +
                ggtitle(title) +
                scale_y_continuous("", limits = 0 to null) +
                scale_x_datetime("")

        ggsave(p, file)
    }
    println("analyzed $repo in $ms ms")
}

class RepoAnalyzer(dir: String) {

    private val repo = FileRepositoryBuilder().setGitDir(File(dir)).build()
    private val lineCountCache = mutableMapOf<ObjectId, Int>()

    fun analyze(): Map<String, Any> {
        val walk = RevWalk(repo)
        val resolve = repo.resolve(repo.branch)
        walk.markStart(walk.parseCommit(resolve))

        val times = mutableListOf<Instant>()
        val lines = mutableListOf<Int>()

        val periodBetween = Duration.ofDays(60)
        var previousTime = Instant.MAX
        for (commit in walk) {
            val time = Instant.ofEpochSecond(commit.commitTime.toLong())

            if (Duration.between(time, previousTime) > periodBetween) {
                times.add(time)
                lines.add(treeSize(commit.tree))
                previousTime = time
            }

            commit.disposeBody()
        }

        walk.dispose()

        return mapOf(
            "times" to times,
            "lines" to lines
        )
    }

    private fun treeSize(tree: RevTree): Int {
        val walk = TreeWalk(repo)
        walk.reset(tree)
        walk.isRecursive = true

        var lines = 0

        while (walk.next())
            if (validFile(walk.nameString))
                lines += repo.objectDatabase.objectSize(walk.getObjectId(0))

        walk.close()

        return lines
    }

    private fun validFile(name: String) =
        name.endsWith(".kt") ||
                name.endsWith(".java") ||
                name.endsWith(".js") ||
                name.endsWith(".ts") ||
                name.endsWith(".m") ||
                name.endsWith(".h") ||
                name.endsWith(".swift")

    private fun ObjectDatabase.objectSize(id: ObjectId): Int =
        lineCountCache.getOrPut(id) { open(id).bytes.count { it == '\n'.toByte() } }
}
