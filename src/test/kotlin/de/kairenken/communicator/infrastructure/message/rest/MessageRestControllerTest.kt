package de.kairenken.communicator.infrastructure.message.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator.application.message.MessageCreation
import de.kairenken.communicator.testdatafactories.*
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(value = [MessageRestController::class])
internal class MessageRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var messageCreationMock: MessageCreation

    @Nested
    @DisplayName("Create Message")
    inner class CreateMessageTest {

        @Test
        fun successfully() {
            every {
                messageCreationMock.createMessage(
                    senderId = MESSAGE_SENDER_ID,
                    chatId = CHAT_ID,
                    content = MESSAGE_CONTENT,
                )
            } returns MessageCreation.Created(message = aTestMessage())

            mockMvc.perform(
                post("/api/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aTestCreateMessageDto().toJson())
            )
                .andExpect(status().isCreated)
                .andExpect(content().json(aTestReadMessageDto().toJson()))
        }

        @Test
        fun `with bad argument`() {
            val badContent = ""

            every {
                messageCreationMock.createMessage(
                    senderId = MESSAGE_SENDER_ID,
                    chatId = CHAT_ID,
                    content = badContent,
                )
            } returns MessageCreation.CreationError(msg = ERROR_MSG)

            mockMvc.perform(
                post("/api/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aTestCreateMessageDto(content = badContent).toJson())
            )
                .andExpect(status().isBadRequest)
                .andExpect(content().json(aTestErrorResponseDto().toJson()))
        }

        @Test
        fun `with non-existing chat`() {
            every {
                messageCreationMock.createMessage(
                    senderId = MESSAGE_SENDER_ID,
                    chatId = CHAT_ID,
                    content = MESSAGE_CONTENT,
                )
            } returns MessageCreation.ChatDoesNotExist(chatId = CHAT_ID)

            mockMvc.perform(
                post("/api/message")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aTestCreateMessageDto().toJson())
            )
                .andExpect(status().isNotFound)
                .andExpect(
                    content()
                        .json(aTestErrorResponseDto(msg = "Chat with id ${CHAT_ID} does not exist").toJson())
                )
        }
    }
}