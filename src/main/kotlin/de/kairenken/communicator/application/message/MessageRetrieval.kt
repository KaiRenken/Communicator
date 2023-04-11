package de.kairenken.communicator.application.message

import de.kairenken.communicator.domain.message.ChatRefRepository
import de.kairenken.communicator.domain.message.Message
import de.kairenken.communicator.domain.message.MessageRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageRetrieval(
    private val messageRepository: MessageRepository,
    private val chatRefRepository: ChatRefRepository,
) {

    fun retrieveAllMessagesByChatId(chatId: UUID): Result {
        if (!chatRefRepository.existsById(chatId = chatId)) {
            return ChatDoesNotExist(chatId = chatId)
        }

        return Retrieved(
            messages = messageRepository.findAllByChatId(chatId)
        )
    }

    sealed class Result
    class Retrieved(val messages: List<Message>) : Result()
    class ChatDoesNotExist(val chatId: UUID) : Result()
}
