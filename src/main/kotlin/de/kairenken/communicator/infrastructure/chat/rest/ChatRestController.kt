package de.kairenken.communicator.infrastructure.chat.rest

import de.kairenken.communicator.application.chat.ChatCreation
import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.infrastructure.chat.rest.model.CreateChatDto
import de.kairenken.communicator.infrastructure.chat.rest.model.ReadChatDto
import de.kairenken.communicator.infrastructure.common.rest.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chat")
class ChatRestController(
    private val chatCreation: ChatCreation,
    private val chatRepository: ChatRepository,
) {

    @PostMapping
    fun createChat(@RequestBody createChatDto: CreateChatDto): ResponseEntity<out Any> =
        createChatDto
            .callUseCase()
            .wrapInResponse()

    @GetMapping
    fun getAllChats() = chatRepository
        .findAll()
        .map { it.toReadChatDto() }
        .wrapInResponse()

    fun CreateChatDto.callUseCase(): ChatCreation.Result =
        chatCreation.createChat(
            name = this.name,
        )

    private fun ChatCreation.Result.wrapInResponse(): ResponseEntity<out Any> = when (this) {
        is ChatCreation.Created -> ResponseEntity(toReadChatDto(), HttpStatus.CREATED)
        is ChatCreation.CreationError -> ResponseEntity(
            mapToErrorResponseDto(),
            HttpStatus.BAD_REQUEST
        )
    }

    private fun ChatCreation.Created.toReadChatDto(): ReadChatDto = ReadChatDto(
        id = this.chat.id,
        name = this.chat.name,
    )

    private fun ChatCreation.CreationError.mapToErrorResponseDto(): ErrorResponseDto =
        ErrorResponseDto(msg = this.msg)

    private fun Chat.toReadChatDto(): ReadChatDto = ReadChatDto(
        id = this.id,
        name = this.name,
    )

    private fun List<ReadChatDto>.wrapInResponse(): ResponseEntity<List<ReadChatDto>> =
        ResponseEntity(this, HttpStatus.OK)
}
