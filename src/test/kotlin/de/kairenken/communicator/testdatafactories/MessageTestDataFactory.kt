package de.kairenken.communicator.testdatafactories

import de.kairenken.communicator.domain.message.Message
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