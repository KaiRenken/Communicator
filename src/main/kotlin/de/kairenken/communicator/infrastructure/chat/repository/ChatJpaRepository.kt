package de.kairenken.communicator.infrastructure.chat.repository

import de.kairenken.communicator.infrastructure.chat.repository.model.ChatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatJpaRepository : JpaRepository<ChatEntity, UUID>