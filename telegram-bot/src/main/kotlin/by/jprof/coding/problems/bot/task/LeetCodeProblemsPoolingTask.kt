package by.jprof.coding.problems.bot.task

import by.jprof.coding.problems.bot.repository.ProblemRepository
import by.jprof.coding.problems.bot.scraper.LeetCodeProblemsScraper
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@Component
class LeetCodeProblemsPoolingTask(
    private val leetCodeProblemsScraper: LeetCodeProblemsScraper,
    private val problemProblemRepository: ProblemRepository,
    private val txOperator : TransactionalOperator,
    ) {

    companion object {
        private val log = LoggerFactory.getLogger(LeetCodeProblemsPoolingTask::class.java)!!
    }

    @Scheduled(cron = "0 0 0 * * 7")
    @Suppress("BlockingMethodInNonBlockingContext") // Scheduled uses separate native thread
    fun runPool() = runBlocking {
        txOperator.executeAndAwait {
            log.info("getting saved leetcode problems")
            val links = problemProblemRepository.findAllProjectedBy().map { it.link }.toSet()
            log.info("scraping all leetcode the problems")
            val scrapedProblems = leetCodeProblemsScraper.scrapeAllLeetCodeProblems()
            log.info("filtering and saving new problems")
            val nonExisting = scrapedProblems.filterNot { links.contains(it.link) }.toList()
            val savedCount = problemProblemRepository.saveAll(nonExisting).count()
            log.info("pool complete $savedCount saved")
        }
    }
}
