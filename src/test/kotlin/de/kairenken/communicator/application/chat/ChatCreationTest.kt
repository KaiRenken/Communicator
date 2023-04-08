package de.kairenken.communicator.application.chat

import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.testdatafactories.CHAT_MEMBER_IDS
import de.kairenken.communicator.testdatafactories.CHAT_NAME
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.Called
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Create Chat")
class ChatCreationTest {

    private val chatRepositoryMock: ChatRepository = mockk()

    private val chatCreationToTest: ChatCreation = ChatCreation(chatRepositoryMock)

    @Test
    fun successfully() {
        justRun { chatRepositoryMock.store(any()) }

        val result = chatCreationToTest.createChat(
            name = CHAT_NAME,
            memberIds = CHAT_MEMBER_IDS
        )

        result.shouldBeInstanceOf<ChatCreation.Created>()
        result.chat.name shouldBe CHAT_NAME
        result.chat.memberIds shouldBe CHAT_MEMBER_IDS
        verify {
            chatRepositoryMock.store(withArg {
                it.name shouldBe CHAT_NAME
                it.memberIds shouldBe CHAT_MEMBER_IDS
            })
        }
    }

    @Test
    fun `with bad argument`() {
        val result = chatCreationToTest.createChat(
            name = "",
            memberIds = CHAT_MEMBER_IDS
        )

        result.shouldBeInstanceOf<ChatCreation.CreationError>()
        verify { chatRepositoryMock wasNot Called }
    }
}