package de.kairenken.communicator.infrastructure.chat.repository

import de.kairenken.communicator.matchers.shouldBeEqualTo
import de.kairenken.communicator.testcontainers.AbstractDatabaseTest
import de.kairenken.communicator.testdatafactories.CHAT_ID
import de.kairenken.communicator.testdatafactories.aTestChat
import de.kairenken.communicator.testdatafactories.aTestChatEntity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(value = [ChatRepositoryImpl::class])
internal class ChatRepositoryImplTest : AbstractDatabaseTest() {

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

    @Nested
    @DisplayName("Check if chat exists by id")
    inner class CheckIfChatExistsByIdTest {

        @Test
        fun `which exists`() {
            chatJpaRepository.save(aTestChatEntity())

            chatRepositoryImplToTest.existsById(CHAT_ID) shouldBe true
        }

        @Test
        fun `which does not exist`() {
            chatRepositoryImplToTest.existsById(CHAT_ID) shouldBe false
        }
    }

    @Nested
    @DisplayName("Find all chats")
    inner class FindAllChatsTest {

        @Test
        fun successfully() {
            chatJpaRepository.save(aTestChatEntity())

            val result = chatRepositoryImplToTest.findAll()

            result shouldHaveSize 1
            result[0] shouldBeEqualTo aTestChat()
        }

        @Test
        fun `with bad name`() {
            chatJpaRepository.save(aTestChatEntity(name = ""))

            shouldThrow<ClassCastException> {
                chatRepositoryImplToTest.findAll()
            }
        }
    }
}