package de.kairenken.communicator.matchers

import de.kairenken.communicator.domain.message.Message
import io.kotest.matchers.shouldBe

infix fun Message.shouldBeEqualTo(other: Message) {
    this.chatId shouldBe other.chatId
    this.content shouldBe other.content
}