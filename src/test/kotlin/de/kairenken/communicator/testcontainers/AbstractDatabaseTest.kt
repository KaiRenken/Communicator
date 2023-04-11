package de.kairenken.communicator.testcontainers

import de.kairenken.communicator.infrastructure.chat.repository.ChatJpaRepository
import de.kairenken.communicator.infrastructure.message.repository.MessageJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [PostgreSQLContextInitializer::class])
abstract class AbstractDatabaseTest {

    @Autowired
    protected lateinit var chatJpaRepository: ChatJpaRepository

    @Autowired
    protected lateinit var messageJpaRepository: MessageJpaRepository

    @BeforeEach
    protected fun tearDown() {
        chatJpaRepository.deleteAll()
        messageJpaRepository.deleteAll()
    }
}