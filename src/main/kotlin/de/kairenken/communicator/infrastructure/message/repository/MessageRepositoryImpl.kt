package de.kairenken.communicator.infrastructure.message.repository

import de.kairenken.communicator.domain.message.Message
import de.kairenken.communicator.domain.message.MessageRepository
import de.kairenken.communicator.infrastructure.message.repository.model.MessageEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MessageRepositoryImpl(private val messageJpaRepository: MessageJpaRepository) : MessageRepository {

    override fun store(message: Message) = message
        .mapToEntity()
        .storeToDb()

    override fun findAllByChatId(chatId: UUID): List<Message> = messageJpaRepository
        .findAllByChatId(chatId = chatId)
        .map { it.toDomain() }

    private fun Message.mapToEntity(): MessageEntity = MessageEntity(
        id = this.id,
        chatId = this.chatId,
        content = this.content,
    )

    private fun MessageEntity.storeToDb() {
        messageJpaRepository.save(this)
    }

    private fun MessageEntity.toDomain() = Message(
        id = this.id,
        chatId = this.chatId,
        content = this.content,
    )
}
