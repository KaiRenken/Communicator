package de.kairenken.communicator.infrastructure.message.rest

import de.kairenken.communicator.application.message.MessageCreation
import de.kairenken.communicator.application.message.MessageRetrieval
import de.kairenken.communicator.domain.message.Message
import de.kairenken.communicator.infrastructure.common.rest.ErrorResponseDto
import de.kairenken.communicator.infrastructure.message.rest.model.CreateMessageDto
import de.kairenken.communicator.infrastructure.message.rest.model.ReadMessageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/message")
class MessageRestController(
    private val messageCreation: MessageCreation,
    private val messageRetrieval: MessageRetrieval,
) {

    @PostMapping
    fun createMessage(@RequestBody createMessageDto: CreateMessageDto): ResponseEntity<out Any> =
        createMessageDto
            .callUseCase()
            .wrapInResponse()

    @GetMapping("/{chatId}")
    fun getMessagesByChatId(@PathVariable(name = "chatId") chatId: UUID): ResponseEntity<out Any> =
        messageRetrieval
            .retrieveAllMessagesByChatId(chatId = chatId)
            .wrapInResponse()

    private fun CreateMessageDto.callUseCase(): MessageCreation.Result =
        messageCreation.createMessage(
            chatId = this.chatId,
            content = this.content,
        )

    private fun MessageCreation.Result.wrapInResponse(): ResponseEntity<out Any> = when (this) {
        is MessageCreation.Created -> ResponseEntity(mapToReadMessageDto(), HttpStatus.CREATED)
        is MessageCreation.ChatDoesNotExist -> ResponseEntity(mapToErrorResponseDto(), HttpStatus.NOT_FOUND)
        is MessageCreation.CreationError -> ResponseEntity(mapToErrorResponseDto(), HttpStatus.BAD_REQUEST)
    }

    private fun MessageCreation.CreationError.mapToErrorResponseDto(): ErrorResponseDto =
        ErrorResponseDto(msg = this.msg)

    private fun MessageCreation.ChatDoesNotExist.mapToErrorResponseDto(): ErrorResponseDto =
        ErrorResponseDto(msg = "Chat with id ${this.chatId} does not exist")

    private fun MessageCreation.Created.mapToReadMessageDto(): ReadMessageDto = ReadMessageDto(
        id = this.message.id,
        chatId = this.message.chatId,
        content = this.message.content,
    )

    private fun MessageRetrieval.Result.wrapInResponse(): ResponseEntity<out Any> = when (this) {
        is MessageRetrieval.Retrieved -> ResponseEntity(mapToReadMessageDtos(), HttpStatus.OK)
        is MessageRetrieval.ChatDoesNotExist -> ResponseEntity(mapToErrorResponseDto(), HttpStatus.NOT_FOUND)
    }

    private fun MessageRetrieval.ChatDoesNotExist.mapToErrorResponseDto() =
        ErrorResponseDto(msg = "Chat with id ${this.chatId} does not exist")

    private fun MessageRetrieval.Retrieved.mapToReadMessageDtos() =
        this.messages.map { it.toReadMessageDto() }

    private fun Message.toReadMessageDto() = ReadMessageDto(
        id = this.id,
        chatId = this.chatId,
        content = this.content,
    )
}
