package de.kairenken.communicator.infrastructure.common.exceptionhandling

import de.kairenken.communicator.application.message.exceptions.ChatDoesNotExistException
import de.kairenken.communicator.domain.chat.ChatInstantiationException
import de.kairenken.communicator.domain.message.MessageInstantiationException
import de.kairenken.communicator.infrastructure.common.rest.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ChatInstantiationException::class])
    fun handleChatInstantiationException(chatInstantiationException: ChatInstantiationException): ResponseEntity<ErrorResponseDto> =
        ResponseEntity(ErrorResponseDto(msg = chatInstantiationException.msg), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [MessageInstantiationException::class])
    fun handleChatInstantiationException(messageInstantiationException: MessageInstantiationException): ResponseEntity<ErrorResponseDto> =
        ResponseEntity(ErrorResponseDto(msg = messageInstantiationException.msg), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(value = [ChatDoesNotExistException::class])
    fun handleChatInstantiationException(chatDoesNotExistException: ChatDoesNotExistException): ResponseEntity<ErrorResponseDto> =
        ResponseEntity(
            ErrorResponseDto(msg = "Chat with id ${chatDoesNotExistException.chatId} does not exist"),
            HttpStatus.NOT_FOUND
        )
}