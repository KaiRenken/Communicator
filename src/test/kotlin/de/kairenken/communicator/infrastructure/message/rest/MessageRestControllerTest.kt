package de.kairenken.communicator.infrastructure.message.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator.application.message.MessageCreation
import de.kairenken.communicator.application.message.MessageRetrieval
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

@WebMvcTest(value = [MessageRestController::class])
internal class MessageRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var messageCreationMock: MessageCreation

    @MockkBean
    private lateinit var messageRetrievalMock: MessageRetrieval


    @Test
    fun `post message`() {
        every {
            messageCreationMock.createMessage(
                chatId = CHAT_ID,
                content = MESSAGE_CONTENT,
            )
        } returns aTestMessage()

        mockMvc.perform(
            post("/api/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aTestCreateMessageDto().toJson())
        )
            .andExpect(status().isCreated)
            .andExpect(content().json(aTestReadMessageDto().toJson()))
    }

    @Test
    fun `get messages from chat`() {
        every {
            messageRetrievalMock.retrieveMessagesFromChat(CHAT_ID)
        } returns listOf(aTestMessage())

        mockMvc.perform(
            get("/api/message/${CHAT_ID}")
        )
            .andExpect(status().isOk)
            .andExpect(content().json("[${aTestReadMessageDto().toJson()}]"))
    }
}