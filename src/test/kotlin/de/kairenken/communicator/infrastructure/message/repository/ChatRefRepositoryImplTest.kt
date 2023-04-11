package de.kairenken.communicator.infrastructure.message.repository

import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.testdatafactories.CHAT_ID
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class ChatRefRepositoryImplTest {

    private val chatRepositoryMock: ChatRepository = mockk()

    private val chatRefRepositoryImplToTest: ChatRefRepositoryImpl =
        ChatRefRepositoryImpl(chatRepository = chatRepositoryMock)

    @Test
    fun `check if chat exists by id which exists`() {
        every { chatRepositoryMock.existsById(CHAT_ID) } returns true

        chatRefRepositoryImplToTest.existsById(CHAT_ID) shouldBe true
    }

    @Test
    fun `check if chat exists by id which does not exist`() {
        every { chatRepositoryMock.existsById(CHAT_ID) } returns false

        chatRefRepositoryImplToTest.existsById(CHAT_ID) shouldBe false
    }
}