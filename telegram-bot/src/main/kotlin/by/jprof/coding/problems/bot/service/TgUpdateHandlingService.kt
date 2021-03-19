package by.jprof.coding.problems.bot.service

import by.jprof.coding.problems.bot.config.text
import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.update.MessageUpdate
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.springframework.stereotype.Service

@Service
class TgUpdateHandlingService(private val telegramBot: TelegramBot) {
    suspend fun handleUpdate(update: Update) {
        if (update is MessageUpdate) {
            update.data.text?.let {
                telegramBot.sendMessage(update.data.chat.id, it)
            }
        }
    }
}


