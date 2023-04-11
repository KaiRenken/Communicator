package de.kairenken.communicator.application.message

import de.kairenken.communicator.application.message.exceptions.ChatDoesNotExistException
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

    fun retrieveMessagesFromChat(chatId: UUID): List<Message> = chatId
        .checkIfChatExists()
        .retrieveMessages()

    private fun UUID.checkIfChatExists(): UUID {
        if (!chatRefRepository.existsById(this)) {
            throw ChatDoesNotExistException(this)
        }

        return this
    }

    private fun UUID.retrieveMessages(): List<Message> = messageRepository.findAllByChatId(this)
}
