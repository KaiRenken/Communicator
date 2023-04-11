package de.kairenken.communicator.infrastructure.chat.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator.application.chat.ChatCreation
import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.testdatafactories.*
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(value = [ChatRestController::class])
internal class ChatRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var chatCreationMock: ChatCreation

    @MockkBean
    private lateinit var chatRepositoryMock: ChatRepository

    @Test
    fun `create chat`() {
        every {
            chatCreationMock.createChat(
                name = CHAT_NAME,
            )
        } returns aTestChat()

        mockMvc.perform(
            post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aTestCreateChatDto().toJson())
        )
            .andExpect(status().isCreated)
            .andExpect(content().json(aTestReadChatDto().toJson()))
    }

    @Test
    fun `get all chats`() {
        every {
            chatRepositoryMock.findAll()
        } returns listOf(aTestChat())

        mockMvc.perform(
            get("/api/chat")
        )
            .andExpect(status().isOk)
            .andExpect(content().json("[${aTestReadChatDto().toJson()}]"))
    }
}