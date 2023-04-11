package de.kairenken.communicator.application.message

import de.kairenken.communicator.domain.message.ChatRefRepository
import de.kairenken.communicator.domain.message.MessageRepository
import de.kairenken.communicator.matchers.shouldBeEqualTo
import de.kairenken.communicator.testdatafactories.CHAT_ID
import de.kairenken.communicator.testdatafactories.aTestChat
import de.kairenken.communicator.testdatafactories.aTestMessage
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.resource.resourceAsBytes
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
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

            val result = messageRetrievalToTest.retrieveAllMessagesByChatId(CHAT_ID)

            result.shouldBeInstanceOf<MessageRetrieval.Retrieved>()
            result.messages shouldHaveSize 1
            result.messages[0] shouldBeEqualTo aTestMessage()
        }

        @Test
        fun `with non-existing chat`() {
            every { chatRefRepositoryMock.existsById(CHAT_ID) } returns false

            val result = messageRetrievalToTest.retrieveAllMessagesByChatId(CHAT_ID)

            result.shouldBeInstanceOf<MessageRetrieval.ChatDoesNotExist>()
            result.chatId shouldBe CHAT_ID
        }
    }
}