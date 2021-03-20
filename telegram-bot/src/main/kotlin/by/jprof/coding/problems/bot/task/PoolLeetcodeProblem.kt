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
class PoolLeetcodeProblem(
    private val leetCodeProblemsScraper: LeetCodeProblemsScraper,
    private val problemRepository: ProblemRepository,
    private val txOperator : TransactionalOperator
    ) {

    companion object {
        private val log = LoggerFactory.getLogger(PoolLeetcodeProblem::class.java)!!
    }


    @Suppress("BlockingMethodInNonBlockingContext") // Scheduled uses separate native thread
    @Scheduled(cron = "0 0 0 * * 7")
    fun runPool() = runBlocking {
        txOperator.executeAndAwait {
            log.info("getting saved leetcode problems")
            val links = problemRepository.findAllProjectedBy().map { it.getLink() }.toSet()
            log.info("scraping all leetcode the problems")
            val scrapedProblems = leetCodeProblemsScraper.scrapeAllLeetCodeProblems()
            scrapedProblems.forEach {log.info(it.difficulty)}
            log.info("filtering and saving new problems")
            val nonExisting = scrapedProblems.filterNot { links.contains(it.link) }.toList()
            val savedCount = problemRepository.saveAll(nonExisting).count()
            log.info("pool complete $savedCount saved")
        }
    }

}