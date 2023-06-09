package de.kairenken.communicator.application.chat

import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.matchers.shouldBeEqualTo
import de.kairenken.communicator.testdatafactories.CHAT_NAME
import de.kairenken.communicator.testdatafactories.aTestChat
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Create Chat")
internal class ChatCreationTest {

    private val chatRepositoryMock: ChatRepository = mockk()

    private val chatCreationToTest: ChatCreation = ChatCreation(chatRepositoryMock)

    @Test
    fun successfully() {
        justRun { chatRepositoryMock.store(any()) }

        val result = chatCreationToTest.createChat(
            name = CHAT_NAME,
        )

        result shouldBeEqualTo aTestChat()
        verify {
            chatRepositoryMock.store(withArg {
                it shouldBeEqualTo aTestChat()
            })
        }
    }
}