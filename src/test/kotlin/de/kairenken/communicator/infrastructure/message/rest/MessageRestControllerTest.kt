package de.kairenken.communicator.infrastructure.message.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator.application.message.MessageCreation
import de.kairenken.communicator.application.message.MessageRetrieval
import de.kairenken.communicator.testdatafactories.*
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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

    @Nested
    @DisplayName("Create Message")
    inner class CreateMessageTest {

        @Test
        fun successfully() {
            every {
                messageCreationMock.createMessage(
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

    @Nested
    @DisplayName("Get all messages by chat id")
    inner class GetMessagesByChatIdTest {

        @Test
        fun successfully() {
            every {
                messageRetrievalMock.retrieveAllMessagesByChatId(chatId = CHAT_ID)
            } returns MessageRetrieval.Retrieved(messages = listOf(aTestMessage()))

            mockMvc.perform(
                get("/api/message/${CHAT_ID}")
            )
                .andExpect(status().isOk)
                .andExpect(content().json("[${aTestReadMessageDto().toJson()}]"))
        }

        @Test
        fun `with non-existing chat`() {
            every {
                messageRetrievalMock.retrieveAllMessagesByChatId(chatId = CHAT_ID)
            } returns MessageRetrieval.ChatDoesNotExist(chatId = CHAT_ID)

            mockMvc.perform(
                get("/api/message/${CHAT_ID}")
            )
                .andExpect(status().isNotFound)
                .andExpect(content().json(aTestErrorResponseDto(msg = "Chat with id ${CHAT_ID} does not exist").toJson()))
        }
    }
}