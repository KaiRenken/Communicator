package de.kairenken.communicator.domain.message

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Create Message")
internal class MessageTest {

    @Test
    fun `without id`() {
        val chatId = UUID.randomUUID()
        val createdMessage = Message(
            chatId = chatId,
            content = "test-content"
        )

        createdMessage.id.shouldBeInstanceOf<UUID>()
        createdMessage.chatId shouldBe chatId
        createdMessage.content shouldBe "test-content"
    }

    @Test
    fun `with id`() {
        val id = UUID.randomUUID()
        val chatId = UUID.randomUUID()
        val createdMessage = Message(
            id = id,
            chatId = chatId,
            content = "test-content"
        )

        createdMessage.id shouldBe id
        createdMessage.chatId shouldBe chatId
        createdMessage.content shouldBe "test-content"
    }

    @Test
    fun `with empty content`() {
        shouldThrow<MessageInstantiationException> {
            Message(
                chatId = UUID.randomUUID(),
                content = "",
            )
        }
            .msg shouldBe "Message.content must not be empty"
    }

    @Test
    fun `with blank content`() {
        shouldThrow<MessageInstantiationException> {
            Message(
                chatId = UUID.randomUUID(),
                content = "  ",
            )
        }
            .msg shouldBe "Message.content must not be blank"
    }
}