package de.kairenken.communicator.application.message.exceptions

import java.util.*

class ChatDoesNotExistException(val chatId: UUID) : Exception()