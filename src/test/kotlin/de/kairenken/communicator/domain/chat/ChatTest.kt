package de.kairenken.communicator.domain.chat

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Create Chat")
internal class ChatTest {

    @Test
    fun `without id`() {
        val creationResult: Chat.Result = Chat(name = "test-name")

        creationResult.shouldBeInstanceOf<Chat.Created>()
        creationResult.chat.id.shouldBeInstanceOf<UUID>()
        creationResult.chat.name shouldBe "test-name"
    }

    @Test
    fun `with id`() {
        val id = UUID.randomUUID()
        val creationResult: Chat.Result = Chat(
            id = id,
            name = "test-name",
        )

        creationResult.shouldBeInstanceOf<Chat.Created>()
        creationResult.chat.id shouldBe id
        creationResult.chat.name shouldBe "test-name"
    }

    @Test
    fun `with empty name`() {
        val creationResult: Chat.Result = Chat(name = "")

        creationResult.shouldBeInstanceOf<Chat.Error>()
        creationResult.msg shouldBe "Chat.name must not be empty"
    }

    @Test
    fun `with blank name`() {
        val creationResult: Chat.Result = Chat(name = "  ")

        creationResult.shouldBeInstanceOf<Chat.Error>()
        creationResult.msg shouldBe "Chat.name must not be blank"
    }

    @Test
    fun `with name too large`() {
        val creationResult: Chat.Result =
            Chat(name = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890")

        creationResult.shouldBeInstanceOf<Chat.Error>()
        creationResult.msg shouldBe "Chat.name must contain less that 100 characters"
    }
}