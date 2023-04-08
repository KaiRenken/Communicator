package de.kairenken.communicator.testdatafactories

import de.kairenken.communicator.domain.chat.Chat
import java.util.*

val CHAT_ID = UUID.randomUUID()
val CHAT_NAME = "test-name"
val CHAT_MEMBER_IDS = listOf<UUID>(UUID.randomUUID(), UUID.randomUUID())

fun aTestChat(
    id: UUID = CHAT_ID,
    name: String = CHAT_NAME,
    memberIds: List<UUID> = CHAT_MEMBER_IDS
): Chat = (
        Chat(
            id = id,
            name = name,
            memberIds = memberIds
        ) as Chat.Created
        ).chat