package de.kairenken.communicator.application.message

import de.kairenken.communicator.domain.message.ChatRefRepository
import de.kairenken.communicator.domain.message.MessageRepository
import de.kairenken.communicator.matchers.shouldBeEqualTo
import de.kairenken.communicator.testdatafactories.CHAT_ID
import de.kairenken.communicator.testdatafactories.MESSAGE_CONTENT
import de.kairenken.communicator.testdatafactories.MESSAGE_SENDER_ID
import de.kairenken.communicator.testdatafactories.aTestMessage
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Create Message")
internal class MessageCreationTest {

    private val messageRepositoryMock: MessageRepository = mockk()
    private val chatRefRepositoryMock: ChatRefRepository = mockk()

    private val messageCreationToTest: MessageCreation = MessageCreation(
        messageRepository = messageRepositoryMock,
        chatRefRepository = chatRefRepositoryMock,
    )

    @Test
    fun successfully() {
        every { chatRefRepositoryMock.existsById(CHAT_ID) } returns true
        justRun { messageRepositoryMock.store(any()) }

        val result = messageCreationToTest.createMessage(
            senderId = MESSAGE_SENDER_ID,
            chatId = CHAT_ID,
            content = MESSAGE_CONTENT,
        )

        result.shouldBeInstanceOf<MessageCreation.Created>()
        result.message.id.shouldBeInstanceOf<UUID>()
        result.message shouldBeEqualTo aTestMessage()
        verify {
            messageRepositoryMock.store(withArg {
                it shouldBeEqualTo aTestMessage()
            })
        }
    }

    @Test
    fun `with bad argument`() {
        val result = messageCreationToTest.createMessage(
            senderId = MESSAGE_SENDER_ID,
            chatId = CHAT_ID,
            content = "",
        )

        result.shouldBeInstanceOf<MessageCreation.CreationError>()
        result.msg shouldBe "Message.content must not be empty"
        verify { messageRepositoryMock wasNot Called }
    }

    @Test
    fun `with non-existing chat`() {
        every { chatRefRepositoryMock.existsById(CHAT_ID) } returns false

        val result = messageCreationToTest.createMessage(
            senderId = MESSAGE_SENDER_ID,
            chatId = CHAT_ID,
            content = MESSAGE_CONTENT,
        )

        result.shouldBeInstanceOf<MessageCreation.ChatDoesNotExist>()
        result.chatId shouldBe CHAT_ID
        verify { messageRepositoryMock wasNot Called }
    }
}