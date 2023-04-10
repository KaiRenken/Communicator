package de.kairenken.communicator.infrastructure.chat.repository

import de.kairenken.communicator.matchers.shouldBeEqualTo
import de.kairenken.communicator.testcontainers.AbstractDatabaseTest
import de.kairenken.communicator.testdatafactories.aTestChat
import de.kairenken.communicator.testdatafactories.aTestChatEntity
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChatRepositoryImplTest : AbstractDatabaseTest() {

    @Test
    fun `store chat succcessfully`() {
        val chatToStore = aTestChat()

        chatRepositoryImpl.store(chat = chatToStore)

        chatJpaRepository.count() shouldBe 1
        val storedChat = chatJpaRepository.findById(chatToStore.id)
        storedChat.get() shouldBeEqualToComparingFields aTestChatEntity()
    }
}