package de.kairenken.communicator.infrastructure.message.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "message")
class MessageEntity(

    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "chat_id")
    val chatId: UUID,

    @Column(name = "content")
    val content: String,
)