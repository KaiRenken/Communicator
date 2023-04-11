package de.kairenken.communicator.infrastructure.chat.rest

import de.kairenken.communicator.application.chat.ChatCreation
import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.domain.chat.ChatRepository
import de.kairenken.communicator.infrastructure.chat.rest.model.CreateChatDto
import de.kairenken.communicator.infrastructure.chat.rest.model.ReadChatDto
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
    fun createChat(@RequestBody createChatDto: CreateChatDto): ResponseEntity<ReadChatDto> = createChatDto
        .callUseCase()
        .toReadChatDto()
        .wrapInCreatedResponse()

    @GetMapping
    fun getAllChats(): ResponseEntity<List<ReadChatDto>> = chatRepository
        .findAll()
        .map { it.toReadChatDto() }
        .wrapInOkResponse()

    fun CreateChatDto.callUseCase(): Chat = chatCreation.createChat(
        name = this.name,
    )

    private fun Chat.toReadChatDto(): ReadChatDto = ReadChatDto(
        id = this.id,
        name = this.name,
    )

    private fun List<ReadChatDto>.wrapInOkResponse(): ResponseEntity<List<ReadChatDto>> =
        ResponseEntity(this, HttpStatus.OK)

    private fun ReadChatDto.wrapInCreatedResponse(): ResponseEntity<ReadChatDto> =
        ResponseEntity(this, HttpStatus.CREATED)
}
