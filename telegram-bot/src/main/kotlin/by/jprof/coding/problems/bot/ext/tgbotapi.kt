package by.jprof.coding.problems.bot.ext

import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.message.content.TextContent

val Message.text get() = ((this as? ContentMessage<*>)?.content as? TextContent)?.text