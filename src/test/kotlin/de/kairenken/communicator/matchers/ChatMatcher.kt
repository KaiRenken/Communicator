package de.kairenken.communicator.matchers

import de.kairenken.communicator.domain.chat.Chat
import de.kairenken.communicator.infrastructure.chat.repository.model.ChatEntity
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

infix fun Chat.shouldBeEqualTo(other: Chat) {
    this.name shouldBe other.name
}