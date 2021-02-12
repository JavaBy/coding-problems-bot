package by.jprof.coding.problems.bot

import com.google.common.base.Stopwatch

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        error("No command provided")
    }
    when(args[0]) { // command
        "scrape-problems" -> {
            val stopwatch = Stopwatch.createStarted()
            val problems = scrapeLeetCodeProblems()
            val elapsed = stopwatch.elapsed()
            print("Scrape took ${elapsed.toHoursPart()} h ${elapsed.toMinutesPart()} min ${elapsed.toSeconds()} sec")
            store(Platform.LEETCODE, problems)
        }
        else -> TODO("Print usage")
    }
}
