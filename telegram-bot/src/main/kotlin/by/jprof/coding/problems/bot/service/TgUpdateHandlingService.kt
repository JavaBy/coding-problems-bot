package by.jprof.coding.problems.bot.service

import by.jprof.coding.problems.bot.handler.UpdateHandler
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.springframework.stereotype.Service

@Service
class TgUpdateHandlingService(private val updateHandlers: List<UpdateHandler>) {
    suspend fun handleUpdate(update: Update) {
        updateHandlers.forEach { it.handleUpdate(update) }
    }
}


