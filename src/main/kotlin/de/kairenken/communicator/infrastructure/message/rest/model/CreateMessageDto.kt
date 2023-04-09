package de.kairenken.communicator.infrastructure.message.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class CreateMessageDto(
    @JsonProperty(required = true) val senderId: UUID,
    @JsonProperty(required = true) val chatId: UUID,
    @JsonProperty(required = true) val content: String,
)