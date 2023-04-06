package de.kairenken.communicator.application.chat

import de.kairenken.communicator.application.chat.model.ChatCreationDto
import de.kairenken.communicator.domain.chat.Chat

class ChatCreation {

    fun createChat(chatCreationDto: ChatCreationDto): Result {
        TODO()
    }

    sealed class Result
    class Created(val chat: Chat) : Result()
    class CreationError(val msg: String) : Result()
}