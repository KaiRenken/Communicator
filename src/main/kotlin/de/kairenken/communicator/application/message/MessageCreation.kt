package de.kairenken.communicator.application.message

import de.kairenken.communicator.application.message.exceptions.ChatDoesNotExistException
import de.kairenken.communicator.domain.message.ChatRefRepository
import de.kairenken.communicator.domain.message.Message
import de.kairenken.communicator.domain.message.MessageRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageCreation(
    private val messageRepository: MessageRepository,
    private val chatRefRepository: ChatRefRepository,
) {

    fun createMessage(
        chatId: UUID,
        content: String,
    ): Message = Message(
        chatId = chatId,
        content = content,
    )
        .checkIfChatExists()
        .storeToDb()

    private fun Message.checkIfChatExists(): Message {
        if (!chatRefRepository.existsById(chatId = this.chatId)) {
            throw ChatDoesNotExistException(chatId = this.chatId)
        }

        return this
    }

    private fun Message.storeToDb(): Message {
        messageRepository.store(message = this)

        return this
    }
}