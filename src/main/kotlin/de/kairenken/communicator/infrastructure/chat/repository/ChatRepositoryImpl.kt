package de.kairenken.communicator.infrastructure.chat.repository

import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.infrastructure.chat.repository.model.ChatEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ChatRepositoryImpl(private val chatJpaRepository: ChatJpaRepository) : ChatRepository {

    override fun store(chat: Chat): Unit = chat
        .mapToEntity()
        .storeToDb()

    override fun existsById(chatId: UUID): Boolean = chatJpaRepository.existsById(chatId)

    private fun Chat.mapToEntity(): ChatEntity = ChatEntity(
        id = this.id,
        name = this.name,
    )

    private fun ChatEntity.storeToDb() {
        chatJpaRepository.save(this)
    }
}