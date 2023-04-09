package de.kairenken.communicator.matchers

import de.kairenken.communicator.domain.chat.Chat
import io.kotest.matchers.shouldBe

infix fun Chat.shouldBeEqualTo(other: Chat) {
    this.name shouldBe other.name
    this.memberIds shouldBe other.memberIds
}