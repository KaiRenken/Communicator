package de.kairenken.communicator.infrastructure.chat.rest.model

import java.util.*

class ReadChatDto(
    val id: UUID,
    val name: String,
    val memberIds: List<UUID>,
)