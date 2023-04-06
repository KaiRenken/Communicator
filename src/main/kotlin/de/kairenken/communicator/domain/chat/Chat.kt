package de.kairenken.communicator.domain.chat

import java.util.*

class Chat private constructor(
    val id: UUID,
    val name: String,
) {

    init {
        require(name.isNotEmpty()) { "Chat.name must not be empty" }
        require(name.isNotBlank()) { "Chat.name must not be blank" }
        require(name.length < 100) { "Chat.name must contain less that 100 characters" }
    }

    companion object {
        operator fun invoke(
            id: UUID = UUID.randomUUID(),
            name: String,
        ) = try {
            Created(
                Chat(
                    id = id,
                    name = name,
                )
            )
        } catch (exception: IllegalArgumentException) {
            Error(exception.message!!)
        }
    }

    sealed class Result
    class Created(val chat: Chat) : Result()
    class Error(val msg: String) : Result()
}