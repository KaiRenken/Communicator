package de.kairenken.communicator.domain.message

import java.util.*

class Message private constructor(
    val id: UUID,
    val chatId: UUID,
    val content: String,
) {

    init {
        require(content.isNotEmpty()) { "Message.content must not be empty" }
        require(content.isNotBlank()) { "Message.content must not be blank" }
    }

    companion object {
        operator fun invoke(
            id: UUID = UUID.randomUUID(),
            chatId: UUID,
            content: String,
        ): Message = try {
            Message(
                id = id,
                chatId = chatId,
                content = content,
            )
        } catch (exception: IllegalArgumentException) {
            throw MessageInstantiationException(msg = exception.message!!)
        }
    }
}

class MessageInstantiationException(val msg: String) : Exception()