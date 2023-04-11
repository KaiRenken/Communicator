package de.kairenken.communicator.domain.chat

import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRepository {

    fun store(chat: Chat)

    fun existsById(chatId: UUID): Boolean
}