package de.kairenken.communicator.domain.chat

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Create Chat")
internal class ChatTest {

    @Test
    fun `without id`() {
        val createdChat = Chat(
            name = "test-name",
        )

        createdChat.id.shouldBeInstanceOf<UUID>()
        createdChat.name shouldBe "test-name"
    }

    @Test
    fun `with id`() {
        val id = UUID.randomUUID()

        val createdChat = Chat(
            id = id,
            name = "test-name",
        )

        createdChat.id shouldBe id
        createdChat.name shouldBe "test-name"
    }

    @Test
    fun `with empty name`() {
        shouldThrow<ChatInstantiationException> {
            Chat(name = "")
        }
            .msg shouldBe "Chat.name must not be empty"
    }

    @Test
    fun `with blank name`() {
        shouldThrow<ChatInstantiationException> {
            Chat(name = "  ")
        }
            .msg shouldBe "Chat.name must not be blank"
    }

    @Test
    fun `with name too large`() {
        shouldThrow<ChatInstantiationException> {
            Chat(name = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890")
        }
            .msg shouldBe "Chat.name must contain less that 100 characters"
    }
}