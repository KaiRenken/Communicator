package de.kairenken.communicator.infrastructure.message.rest

import de.kairenken.communicator.application.message.MessageCreation
import de.kairenken.communicator.infrastructure.common.rest.ErrorResponseDto
import de.kairenken.communicator.infrastructure.message.rest.model.CreateMessageDto
import de.kairenken.communicator.infrastructure.message.rest.model.ReadMessageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/message")
class MessageRestController(
    private val messageCreation: MessageCreation,
) {

    @PostMapping
    fun createMessage(@RequestBody createMessageDto: CreateMessageDto): ResponseEntity<out Any> =
        createMessageDto
            .callUseCase()
            .wrapInResponse()

    private fun CreateMessageDto.callUseCase(): MessageCreation.Result =
        messageCreation.createMessage(
            senderId = this.senderId,
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
        senderId = this.message.senderId,
        chatId = this.message.chatId,
        content = this.message.content,
    )
}
