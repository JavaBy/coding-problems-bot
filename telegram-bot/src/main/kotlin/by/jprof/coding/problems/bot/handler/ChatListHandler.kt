package by.jprof.coding.problems.bot.handler

import by.jprof.coding.problems.bot.domain.Chat
import by.jprof.coding.problems.bot.repository.ChatRepository
import dev.inmo.tgbotapi.extensions.utils.asChatEventMessage
import dev.inmo.tgbotapi.types.message.ChatEvents.LeftChatMember
import dev.inmo.tgbotapi.types.message.ChatEvents.NewChatMembers
import dev.inmo.tgbotapi.types.update.MessageUpdate
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait

class ChatListHandler(
    private val chatRepository: ChatRepository,
    private val txOperator : TransactionalOperator
    ) : UpdateHandler {
    companion object {
        private val MY_NAME = System.getenv("TG_BOT_NAME")!!
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    override suspend fun handleUpdate(update: Update) {
        if (update is MessageUpdate) {
            val message = update.data.asChatEventMessage()
            val chatId = update.data.chat.id.toString()

            message?.let { chatEventMessage ->
                txOperator.executeAndAwait {

                    val event = chatEventMessage.chatEvent
                    when(event) {
                        is NewChatMembers -> {
                            if (event.members.any { MY_NAME == it.username?.username }) {
                                val chat: Chat? = chatRepository.findById(chatId)
                                if (chat == null) {
                                    chatRepository.save(Chat(chatId))
                                }
                            }
                        }
                        is LeftChatMember -> {
                            if (MY_NAME == event.user.username?.username) {
                                chatRepository.findById(chatId)?.let { chatRepository.delete(it) }
                            }
                        }
                    }

                }


            }
        }
    }
}