package de.kairenken.communicator.domain.chat

import org.springframework.stereotype.Repository

@Repository
interface ChatRepository {

    fun store(chat: Chat)
}