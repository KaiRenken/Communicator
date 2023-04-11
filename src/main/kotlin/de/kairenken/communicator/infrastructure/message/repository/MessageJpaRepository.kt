package de.kairenken.communicator.infrastructure.message.repository

import de.kairenken.communicator.infrastructure.message.repository.model.MessageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageJpaRepository : JpaRepository<MessageEntity, UUID> {

    fun findAllByChatId(chatId: UUID): List<MessageEntity>
}