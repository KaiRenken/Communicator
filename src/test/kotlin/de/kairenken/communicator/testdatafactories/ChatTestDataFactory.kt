package de.kairenken.communicator.testdatafactories

import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.infrastructure.chat.repository.model.ChatEntity
import de.kairenken.communicator.infrastructure.chat.rest.model.CreateChatDto
import de.kairenken.communicator.infrastructure.chat.rest.model.ReadChatDto
import org.json.JSONObject
import java.util.*

val CHAT_ID = UUID.randomUUID()
val CHAT_NAME = "test-name"

fun aTestChat(
    id: UUID = CHAT_ID,
    name: String = CHAT_NAME,
): Chat = (
        Chat(
            id = id,
            name = name,
        ) as Chat.Created
        ).chat

fun aTestChatEntity(
    id: UUID = CHAT_ID,
    name: String = CHAT_NAME,
): ChatEntity = ChatEntity(
    id = id,
    name = name,
)

fun aTestCreateChatDto(
    name: String = CHAT_NAME,
): CreateChatDto = CreateChatDto(
    name = name,
)

fun aTestReadChatDto(
    id: UUID = CHAT_ID,
    name: String = CHAT_NAME,
): ReadChatDto = ReadChatDto(
    id = id,
    name = name,
)

fun CreateChatDto.toJson(): String = JSONObject()
    .put("name", this.name)
    .toString()

fun ReadChatDto.toJson(): String = JSONObject()
    .put("id", this.id)
    .put("name", this.name)
    .toString()