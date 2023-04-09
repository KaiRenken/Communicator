package de.kairenken.communicator.testdatafactories

import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.infrastructure.chat.rest.model.CreateChatDto
import de.kairenken.communicator.infrastructure.chat.rest.model.ReadChatDto
import org.json.JSONArray
import org.json.JSONObject
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

fun aTestCreateChatDto(
    name: String = CHAT_NAME,
    memberIds: List<UUID> = CHAT_MEMBER_IDS,
): CreateChatDto = CreateChatDto(
    name = name,
    memberIds = memberIds,
)

fun aTestReadChatDto(
    id: UUID = CHAT_ID,
    name: String = CHAT_NAME,
    memberIds: List<UUID> = CHAT_MEMBER_IDS,
): ReadChatDto = ReadChatDto(
    id = id,
    name = name,
    memberIds = memberIds,
)

fun CreateChatDto.toJson(): String = JSONObject()
    .put("name", this.name)
    .put("memberIds", JSONArray(this.memberIds.map { it.toString() }))
    .toString()

fun ReadChatDto.toJson(): String = JSONObject()
    .put("id", this.id)
    .put("name", this.name)
    .put("memberIds", JSONArray(this.memberIds.map { it.toString() }))
    .toString()