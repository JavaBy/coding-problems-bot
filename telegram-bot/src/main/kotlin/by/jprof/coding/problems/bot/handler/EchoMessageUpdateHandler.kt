package by.jprof.coding.problems.bot.handler

import by.jprof.coding.problems.bot.ext.text
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.update.MessageUpdate
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.springframework.stereotype.Component

@Component
class EchoMessageUpdateHandler(private val telegramBot: TelegramBot) : UpdateHandler {
    override suspend fun handleUpdate(update: Update) {
        if (update is MessageUpdate) {
            update.data.text?.let {
                telegramBot.sendMessage(update.data.chat.id, "echo $it")
            }
        }
    }
}