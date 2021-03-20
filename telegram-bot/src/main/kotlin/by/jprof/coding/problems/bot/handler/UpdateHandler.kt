package by.jprof.coding.problems.bot.handler

import dev.inmo.tgbotapi.types.update.abstracts.Update

interface UpdateHandler {
    suspend fun handleUpdate(update: Update)
}