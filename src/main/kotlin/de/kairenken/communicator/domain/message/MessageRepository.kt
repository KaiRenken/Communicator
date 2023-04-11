package de.kairenken.communicator.domain.message

import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageRepository {

    fun store(message: Message)

    fun findAllByChatId(chatId: UUID): List<Message>
}