package de.kairenken.communicator.domain.message

import org.springframework.stereotype.Repository

@Repository
interface MessageRepository {

    fun store(message: Message)
}