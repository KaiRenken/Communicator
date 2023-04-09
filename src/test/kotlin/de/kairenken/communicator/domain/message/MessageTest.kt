package de.kairenken.communicator.domain.message

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Create Message")
internal class MessageTest {

    @Test
    fun `without id`() {
        val senderId = UUID.randomUUID()
        val chatId = UUID.randomUUID()
        val creationResult: Message.Result = Message(
            senderId = senderId,
            chatId = chatId,
            content = "test-content"
        )

        creationResult.shouldBeInstanceOf<Message.Created>()
        creationResult.message.id.shouldBeInstanceOf<UUID>()
        creationResult.message.senderId shouldBe senderId
        creationResult.message.chatId shouldBe chatId
        creationResult.message.content shouldBe "test-content"
    }

    @Test
    fun `with id`() {
        val id = UUID.randomUUID()
        val senderId = UUID.randomUUID()
        val chatId = UUID.randomUUID()
        val creationResult: Message.Result = Message(
            id = id,
            senderId = senderId,
            chatId = chatId,
            content = "test-content"
        )

        creationResult.shouldBeInstanceOf<Message.Created>()
        creationResult.message.id shouldBe id
        creationResult.message.senderId shouldBe senderId
        creationResult.message.chatId shouldBe chatId
        creationResult.message.content shouldBe "test-content"
    }

    @Test
    fun `with empty content`() {
        val creationResult: Message.Result = Message(
            senderId = UUID.randomUUID(),
            chatId = UUID.randomUUID(),
            content = "",
        )

        creationResult.shouldBeInstanceOf<Message.Error>()
        creationResult.msg shouldBe "Message.content must not be empty"
    }

    @Test
    fun `with blank name`() {
        val creationResult: Message.Result = Message(
            senderId = UUID.randomUUID(),
            chatId = UUID.randomUUID(),
            content = "  ",
        )

        creationResult.shouldBeInstanceOf<Message.Error>()
        creationResult.msg shouldBe "Message.content must not be blank"
    }
}