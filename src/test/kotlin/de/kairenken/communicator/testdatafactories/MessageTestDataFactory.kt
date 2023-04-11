package de.kairenken.communicator.testdatafactories

import de.kairenken.communicator.domain.message.Message
import de.kairenken.communicator.infrastructure.message.repository.model.MessageEntity
import de.kairenken.communicator.infrastructure.message.rest.model.CreateMessageDto
import de.kairenken.communicator.infrastructure.message.rest.model.ReadMessageDto
import org.json.JSONObject
import java.util.*


val MESSAGE_ID = UUID.randomUUID()
val MESSAGE_SENDER_ID = UUID.randomUUID()
val MESSAGE_CONTENT = "test-content"

fun aTestMessage(
    id: UUID = MESSAGE_ID,
    senderId: UUID = MESSAGE_SENDER_ID,
    chatId: UUID = CHAT_ID,
    content: String = MESSAGE_CONTENT,
): Message = (
        Message(
            id = id,
            senderId = senderId,
            chatId = chatId,
            content = content,
        ) as Message.Created
        ).message

fun aTestMessageEntity(
    id: UUID = MESSAGE_ID,
    senderId: UUID = MESSAGE_SENDER_ID,
    chatId: UUID = CHAT_ID,
    content: String = MESSAGE_CONTENT,
): MessageEntity = MessageEntity(
    id = id,
    senderId = senderId,
    chatId = chatId,
    content = content,
)

fun aTestCreateMessageDto(
    senderId: UUID = MESSAGE_SENDER_ID,
    chatId: UUID = CHAT_ID,
    content: String = MESSAGE_CONTENT,
): CreateMessageDto = CreateMessageDto(
    senderId = senderId,
    chatId = chatId,
    content = content,
)

fun aTestReadMessageDto(
    id: UUID = MESSAGE_ID,
    senderId: UUID = MESSAGE_SENDER_ID,
    chatId: UUID = CHAT_ID,
    content: String = MESSAGE_CONTENT,
): ReadMessageDto = ReadMessageDto(
    id = id,
    senderId = senderId,
    chatId = chatId,
    content = content,
)

fun CreateMessageDto.toJson(): String = JSONObject()
    .put("senderId", this.senderId)
    .put("chatId", this.chatId)
    .put("content", this.content)
    .toString()

fun ReadMessageDto.toJson(): String = JSONObject()
    .put("id", this.id)
    .put("senderId", this.senderId)
    .put("chatId", this.chatId)
    .put("content", this.content)
    .toString()