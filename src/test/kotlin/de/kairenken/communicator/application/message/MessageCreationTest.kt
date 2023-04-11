package de.kairenken.communicator.application.message

import de.kairenken.communicator.application.message.exceptions.ChatDoesNotExistException
import de.kairenken.communicator.domain.message.ChatRefRepository
import de.kairenken.communicator.domain.message.MessageRepository
import de.kairenken.communicator.matchers.shouldBeEqualTo
import de.kairenken.communicator.testdatafactories.CHAT_ID
import de.kairenken.communicator.testdatafactories.MESSAGE_CONTENT
import de.kairenken.communicator.testdatafactories.aTestMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
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

        val createdMessage = messageCreationToTest.createMessage(
            chatId = CHAT_ID,
            content = MESSAGE_CONTENT,
        )

        createdMessage shouldBeEqualTo aTestMessage()
        verify {
            messageRepositoryMock.store(withArg {
                it shouldBeEqualTo aTestMessage()
            })
        }
    }

    @Test
    fun `with non-existing chat`() {
        every { chatRefRepositoryMock.existsById(CHAT_ID) } returns false

        shouldThrow<ChatDoesNotExistException> {
            messageCreationToTest.createMessage(
                chatId = CHAT_ID,
                content = MESSAGE_CONTENT,
            )
        }
            .chatId shouldBe CHAT_ID
    }
}