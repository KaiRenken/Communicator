package de.kairenken.communicator.domain.chat

import java.util.*

class Chat private constructor(
    val id: UUID,
    val name: String,
    val memberIds: List<UUID>,
) {

    init {
        require(name.isNotEmpty()) { "Chat.name must not be empty" }
        require(name.isNotBlank()) { "Chat.name must not be blank" }
        require(name.length < 100) { "Chat.name must contain less that 100 characters" }
        require(memberIds.isNotEmpty()) { "Chat.memberIds must not be empty" }
    }

    companion object {
        operator fun invoke(
            id: UUID = UUID.randomUUID(),
            name: String,
            memberIds: List<UUID>,
        ): Result = try {
            Created(
                Chat(
                    id = id,
                    name = name,
                    memberIds = memberIds,
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