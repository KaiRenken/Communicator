package de.kairenken.communicator.application.message

import de.kairenken.communicator.application.message.exceptions.ChatDoesNotExistException
import de.kairenken.communicator.domain.message.ChatRefRepository
import de.kairenken.communicator.domain.message.MessageRepository
import de.kairenken.communicator.matchers.shouldBeEqualTo
import de.kairenken.communicator.testdatafactories.CHAT_ID
import de.kairenken.communicator.testdatafactories.aTestMessage
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class MessageRetrievalTest {

    private val messageRepositoryMock: MessageRepository = mockk()
    private val chatRefRepositoryMock: ChatRefRepository = mockk()

    private val messageRetrievalToTest: MessageRetrieval = MessageRetrieval(
        messageRepository = messageRepositoryMock,
        chatRefRepository = chatRefRepositoryMock,
    )

    @Nested
    @DisplayName("Retrieve all messages by chat id")
    inner class RetrieveByChatIdTest {

        @Test
        fun successfully() {
            every { chatRefRepositoryMock.existsById(CHAT_ID) } returns true
            every { messageRepositoryMock.findAllByChatId(CHAT_ID) } returns listOf(aTestMessage())

            val retrievedMessages = messageRetrievalToTest.retrieveMessagesFromChat(CHAT_ID)

            retrievedMessages shouldHaveSize 1
            retrievedMessages[0] shouldBeEqualTo aTestMessage()
        }

        @Test
        fun `with non-existing chat`() {
            every { chatRefRepositoryMock.existsById(CHAT_ID) } returns false

            shouldThrow<ChatDoesNotExistException> {
                messageRetrievalToTest.retrieveMessagesFromChat(CHAT_ID)
            }
                .chatId shouldBe CHAT_ID
        }
    }
}