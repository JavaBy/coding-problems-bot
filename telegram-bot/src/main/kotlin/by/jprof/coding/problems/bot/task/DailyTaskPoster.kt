package by.jprof.coding.problems.bot.task

import by.jprof.coding.problems.bot.domain.Messenger
import by.jprof.coding.problems.bot.repository.ChatRepository
import by.jprof.coding.problems.bot.repository.ProblemRepository
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

@Component
class DailyTaskPoster(
    private val problemProblemRepository: ProblemRepository,
    private val chatRepository: ChatRepository,
    private val txOperator : TransactionalOperator,
    private val tgBot : TelegramBot
) {
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