package de.kairenken.communicator.infrastructure.chat.repository

import de.kairenken.communicator.testcontainers.AbstractDatabaseTest
import de.kairenken.communicator.testdatafactories.CHAT_ID
import de.kairenken.communicator.testdatafactories.aTestChat
import de.kairenken.communicator.testdatafactories.aTestChatEntity
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(value = [ChatRepositoryImpl::class])
class ChatRepositoryImplTest : AbstractDatabaseTest() {

    @Autowired
    protected lateinit var chatRepositoryImplToTest: ChatRepositoryImpl

    @Test
    fun `store chat succcessfully`() {
        val chatToStore = aTestChat()

        chatRepositoryImplToTest.store(chat = chatToStore)

        chatJpaRepository.count() shouldBe 1
        val storedChat = chatJpaRepository.findById(chatToStore.id)
        storedChat.get() shouldBeEqualToComparingFields aTestChatEntity()
    }

    @Test
    fun `check if chat exists by id which exists`() {
        chatJpaRepository.save(aTestChatEntity())

        chatRepositoryImplToTest.existsById(CHAT_ID) shouldBe true
    }

    @Test
    fun `check if chat exists by id which does not exist`() {
        chatRepositoryImplToTest.existsById(CHAT_ID) shouldBe false
    }
}