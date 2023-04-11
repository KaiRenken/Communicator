package de.kairenken.communicator.infrastructure.message.rest.model

import java.util.*

class ReadMessageDto(
    val id: UUID,
    val chatId: UUID,
    val content: String,
)