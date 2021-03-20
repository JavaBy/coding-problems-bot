package by.jprof.coding.problems.bot.task

import by.jprof.coding.problems.bot.domain.Messenger
import by.jprof.coding.problems.bot.repository.ChatRepository
import by.jprof.coding.problems.bot.repository.ProblemRepository
import by.jprof.coding.problems.bot.scraper.LeetCodeProblemsScraper
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@Component
class PoolLeetcodeProblem(
    private val leetCodeProblemsScraper: LeetCodeProblemsScraper,
    private val problemProblemRepository: ProblemRepository,
    private val chatRepository: ChatRepository,
    private val txOperator : TransactionalOperator,
    private val tgBot : TelegramBot
    ) {

    companion object {
        private val log = LoggerFactory.getLogger(PoolLeetcodeProblem::class.java)!!
    }


    @Suppress("BlockingMethodInNonBlockingContext") // Scheduled uses separate native thread
    @Scheduled(cron = "0 0 0 * * 7")
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

    @Scheduled(cron = "0 0 12 * * *")
    fun postDailyTask() = runBlocking {
        txOperator.executeAndAwait {
            val problems = problemProblemRepository.findAll().toList()
            val randomProblem = problems.random()
            val chatMessage = "Hi all! Here is yours coding problem for today:\n${randomProblem.link}"
            chatRepository.findAll()
                .filter { it.messenger == Messenger.TELEGRAM.messenger }
                .collect { tgBot.sendMessage(ChatId(it.id.toLong()), chatMessage)}
        }
    }

}