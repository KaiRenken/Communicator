package de.kairenken.communicator.infrastructure.message.repository

import de.kairenken.communicator.domain.message.Message
import de.kairenken.communicator.domain.message.MessageRepository
import de.kairenken.communicator.infrastructure.message.repository.model.MessageEntity
import org.springframework.stereotype.Repository

@Repository
class MessageRepositoryImpl(private val messageJpaRepository: MessageJpaRepository) : MessageRepository {

    override fun store(message: Message) = message
        .mapToEntity()
        .storeToDb()

    private fun Message.mapToEntity(): MessageEntity = MessageEntity(
        id = this.id,
        senderId = this.senderId,
        chatId = this.chatId,
        content = this.content,
    )

    private fun MessageEntity.storeToDb() {
        messageJpaRepository.save(this)
    }
}
