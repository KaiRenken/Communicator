package de.kairenken.communicator.infrastructure.chat.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "member")
data class MemberEntity (

    @Id
    @Column(name = "id")
    val id: UUID,
)