package de.kairenken.communicator.testcontainers

import de.kairenken.communicator.infrastructure.chat.repository.ChatJpaRepository
import de.kairenken.communicator.infrastructure.chat.repository.ChatRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@Import(value = [ChatRepositoryImpl::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [PostgreSQLContextInitializer::class])
abstract class AbstractDatabaseTest {

    @Autowired
    protected lateinit var chatRepositoryImpl: ChatRepositoryImpl

    @Autowired
    protected lateinit var chatJpaRepository: ChatJpaRepository

    @BeforeEach
    protected fun tearDown() {
        chatJpaRepository.deleteAll()
    }
}