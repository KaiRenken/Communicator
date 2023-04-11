package de.kairenken.communicator.infrastructure.message.repository

import de.kairenken.communicator.matchers.shouldBeEqualTo
import de.kairenken.communicator.testcontainers.AbstractDatabaseTest
import de.kairenken.communicator.testdatafactories.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(value = [MessageRepositoryImpl::class])
internal class MessageRepositoryImplTest : AbstractDatabaseTest() {

    @Autowired
    protected lateinit var messageRepositoryImplToTest: MessageRepositoryImpl

    @Test
    fun `store message succcessfully`() {
        chatJpaRepository.save(aTestChatEntity())
        messageRepositoryImplToTest.store(message = aTestMessage())

        messageJpaRepository.count() shouldBe 1
        val storedMessage = messageJpaRepository.findById(MESSAGE_ID)
        storedMessage.get() shouldBeEqualToComparingFields aTestMessageEntity()
    }

    @Nested
    @DisplayName("Find all messages by chat id")
    inner class FindAllByChatIdTest {

        @Test
        fun successfully() {
            chatJpaRepository.save(aTestChatEntity())
            messageJpaRepository.save(aTestMessageEntity())

            val result = messageRepositoryImplToTest.findAllByChatId(CHAT_ID)

            result shouldHaveSize 1
            result[0] shouldBeEqualTo aTestMessage()
        }

        @Test
        fun `with bad content`() {
            chatJpaRepository.save(aTestChatEntity())
            messageJpaRepository.save(aTestMessageEntity(content = ""))

            shouldThrow<ClassCastException> {
                messageRepositoryImplToTest.findAllByChatId(CHAT_ID)
            }
        }
    }
}