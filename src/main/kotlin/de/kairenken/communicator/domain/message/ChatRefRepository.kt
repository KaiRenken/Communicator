package de.kairenken.communicator.domain.message

import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatRefRepository {

    fun existsById(chatId: UUID): Boolean
}
