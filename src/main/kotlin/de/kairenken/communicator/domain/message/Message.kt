package de.kairenken.communicator.domain.message

import java.util.*

class Message private constructor(
    val id: UUID,
    val senderId: UUID,
    val content: String,
) {

    init {
        require(content.isNotEmpty()) { "Message.content must not be empty" }
        require(content.isNotBlank()) { "Message.content must not be blank" }
    }

    companion object {
        operator fun invoke(
            id: UUID = UUID.randomUUID(),
            senderId: UUID,
            content: String,
        ): Result = try {
            Created(
                Message(
                    id = id,
                    senderId = senderId,
                    content = content,
                )
            )
        } catch (exception: IllegalArgumentException) {
            Error(exception.message!!)
        }
    }

    sealed class Result
    class Created(val message: Message) : Result()
    class Error(val msg: String) : Result()
}