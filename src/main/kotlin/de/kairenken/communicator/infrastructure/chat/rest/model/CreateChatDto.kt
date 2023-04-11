package de.kairenken.communicator.infrastructure.chat.rest.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class CreateChatDto(
    @JsonProperty(required = true) val name: String,
)