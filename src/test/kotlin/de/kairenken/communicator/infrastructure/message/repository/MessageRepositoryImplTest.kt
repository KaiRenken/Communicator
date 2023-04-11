package de.kairenken.communicator.infrastructure.message.repository

import de.kairenken.communicator.testcontainers.AbstractDatabaseTest
import de.kairenken.communicator.testdatafactories.MESSAGE_ID
import de.kairenken.communicator.testdatafactories.aTestChatEntity
import de.kairenken.communicator.testdatafactories.aTestMessage
import de.kairenken.communicator.testdatafactories.aTestMessageEntity
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(value = [MessageRepositoryImpl::class])
class MessageRepositoryImplTest : AbstractDatabaseTest() {

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
}