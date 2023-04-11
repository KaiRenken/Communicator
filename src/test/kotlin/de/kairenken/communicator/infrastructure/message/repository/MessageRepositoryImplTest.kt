package de.kairenken.communicator.infrastructure.message.repository

import de.kairenken.communicator.testcontainers.AbstractDatabaseTest
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
        val messageToStore = aTestMessage()

        messageRepositoryImplToTest.store(message = messageToStore)

        messageJpaRepository.count() shouldBe 1
        val storedMessage = messageJpaRepository.findById(messageToStore.id)
        storedMessage.get() shouldBeEqualToComparingFields aTestMessageEntity()
    }
}