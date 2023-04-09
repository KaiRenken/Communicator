package de.kairenken.communicator.infrastructure.chat.rest

import de.kairenken.communicator.application.chat.ChatCreation
import de.kairenken.communicator.infrastructure.chat.rest.model.CreateChatDto
import de.kairenken.communicator.infrastructure.chat.rest.model.ReadChatDto
import de.kairenken.communicator.infrastructure.common.rest.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatRestController(private val chatCreation: ChatCreation) {

    @PostMapping
    fun createChat(@RequestBody createChatDto: CreateChatDto): ResponseEntity<out Any> =
        createChatDto
            .callUseCase()
            .wrapInResponse()

    private fun CreateChatDto.callUseCase(): ChatCreation.Result =
        chatCreation.createChat(
            name = this.name,
            memberIds = this.memberIds,
        )

    private fun ChatCreation.Result.wrapInResponse(): ResponseEntity<out Any> = when (this) {
        is ChatCreation.Created -> ResponseEntity(mapToReadChatDto(), HttpStatus.CREATED)
        is ChatCreation.CreationError -> ResponseEntity(
            mapToErrorResponseDto(),
            HttpStatus.BAD_REQUEST
        )
    }

    private fun ChatCreation.Created.mapToReadChatDto(): ReadChatDto = ReadChatDto(
        id = this.chat.id,
        name = this.chat.name,
        memberIds = this.chat.memberIds,
    )

    private fun ChatCreation.CreationError.mapToErrorResponseDto(): ErrorResponseDto =
        ErrorResponseDto(msg = this.msg)
}
