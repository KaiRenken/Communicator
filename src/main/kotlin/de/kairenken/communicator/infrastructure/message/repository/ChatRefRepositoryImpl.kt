package de.kairenken.communicator.infrastructure.message.repository

import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.domain.message.ChatRefRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ChatRefRepositoryImpl(private val chatRepository: ChatRepository) : ChatRefRepository {

    override fun existsById(chatId: UUID): Boolean = chatRepository.existsById(chatId = chatId)
}