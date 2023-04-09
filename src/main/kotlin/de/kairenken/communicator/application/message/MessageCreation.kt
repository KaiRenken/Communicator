package de.kairenken.communicator.application.message

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
        senderId: UUID,
        chatId: UUID,
        content: String,
    ): Result = createDomainObject(
        senderId = senderId,
        chatId = chatId,
        content = content,
    )
        .checkIfChatExists()
        .storeToDb()

    private fun createDomainObject(
        senderId: UUID,
        chatId: UUID,
        content: String
    ): Message.Result = Message(
        senderId = senderId,
        chatId = chatId,
        content = content,
    )

    private fun Message.Result.checkIfChatExists(): Result = when (this) {
        is Message.Created -> {
            if (chatRefRepository.existsById(chatId = this.message.chatId)) {
                Created(message = this.message)
            } else {
                ChatDoesNotExist(this.message.chatId)
            }
        }

        is Message.Error -> CreationError(msg = this.msg)
    }

    private fun Result.storeToDb(): Result = when (this) {
        is Created -> {
            messageRepository.store(message = this.message)
            this
        }

        else -> this
    }

    sealed class Result
    class Created(val message: Message) : Result()
    class CreationError(val msg: String) : Result()
    class ChatDoesNotExist(val chatId: UUID) : Result()
}
