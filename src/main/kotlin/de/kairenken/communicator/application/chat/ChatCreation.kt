package de.kairenken.communicator.application.chat

import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.domain.chat.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatCreation(private val chatRepository: ChatRepository) {

    fun createChat(
        name: String,
    ): Chat = createDomainObject(
        name = name,
    )
        .storeToDb()

    private fun createDomainObject(name: String): Chat = Chat(
        name = name,
    )

    private fun Chat.storeToDb(): Chat {
        chatRepository.store(chat = this)

        return this
    }
}
