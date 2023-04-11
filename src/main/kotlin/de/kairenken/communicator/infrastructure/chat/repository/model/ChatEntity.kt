package de.kairenken.communicator.infrastructure.chat.repository.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "chat")
class ChatEntity(

    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "name")
    val name: String,
)