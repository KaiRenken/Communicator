package de.kairenken.communicator.application.chat

import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.domain.chat.ChatRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatCreation(private val chatRepository: ChatRepository) {

    fun createChat(
        name: String,
        memberIds: List<UUID>,
    ): Result = createDomainObject(
        name = name,
        memberIds = memberIds,
    )
        .storeToDb()

    private fun createDomainObject(name: String, memberIds: List<UUID>): Chat.Result = Chat(
        name = name,
        memberIds = memberIds,
    )

    private fun Chat.Result.storeToDb(): Result = when (this) {
        is Chat.Created -> {
            chatRepository.store(chat = this.chat)
            Created(chat = this.chat)
        }

        is Chat.Error -> CreationError(msg = this.msg)
    }

    sealed class Result
    class Created(val chat: Chat) : Result()
    class CreationError(val msg: String) : Result()
}
