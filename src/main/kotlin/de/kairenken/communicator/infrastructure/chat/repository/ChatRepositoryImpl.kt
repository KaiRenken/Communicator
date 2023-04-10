package de.kairenken.communicator.infrastructure.chat.repository

import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.infrastructure.chat.repository.model.ChatEntity
import de.kairenken.communicator.infrastructure.chat.repository.model.MemberEntity
import org.springframework.stereotype.Repository

@Repository
class ChatRepositoryImpl(private val chatJpaRepository: ChatJpaRepository) : ChatRepository {

    override fun store(chat: Chat): Unit = chat
        .mapToEntity()
        .storeToDb()

    private fun Chat.mapToEntity(): ChatEntity = ChatEntity(
        id = this.id,
        name = this.name,
        members = this.memberIds.map { MemberEntity(id = it) }
    )

    private fun ChatEntity.storeToDb() {
        chatJpaRepository.save(this)
    }
}