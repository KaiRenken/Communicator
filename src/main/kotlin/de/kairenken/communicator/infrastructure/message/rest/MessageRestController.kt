package de.kairenken.communicator.infrastructure.message.rest

import de.kairenken.communicator.application.message.MessageCreation
import de.kairenken.communicator.application.message.MessageRetrieval
import de.kairenken.communicator.domain.message.Message
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
    fun createMessage(@RequestBody createMessageDto: CreateMessageDto): ResponseEntity<ReadMessageDto> =
        createMessageDto
            .callCreationUseCase()
            .toReadMessageDto()
            .wrapInCreatedResponse()

    @GetMapping("/{chatId}")
    fun getMessagesByChatId(@PathVariable(name = "chatId") chatId: UUID): ResponseEntity<List<ReadMessageDto>> =
        chatId
            .callRetrievalUseCase()
            .map { it.toReadMessageDto() }
            .wrapInOkResponse()

    private fun CreateMessageDto.callCreationUseCase(): Message = messageCreation.createMessage(
        chatId = this.chatId,
        content = this.content,
    )

    private fun Message.toReadMessageDto() = ReadMessageDto(
        id = this.id,
        chatId = this.chatId,
        content = this.content,
    )

    private fun ReadMessageDto.wrapInCreatedResponse(): ResponseEntity<ReadMessageDto> =
        ResponseEntity(this, HttpStatus.CREATED)

    private fun UUID.callRetrievalUseCase(): List<Message> = messageRetrieval.retrieveMessagesFromChat(this)

    private fun List<ReadMessageDto>.wrapInOkResponse(): ResponseEntity<List<ReadMessageDto>> =
        ResponseEntity(this, HttpStatus.OK)
}
