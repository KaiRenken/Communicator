package de.kairenken.communicator.testcontainers

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class PostgreSQLContextInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(context: ConfigurableApplicationContext) {
        postgres.start()
        TestPropertyValues.of(
            "spring.datasource.url=${postgres.jdbcUrl}",
            "spring.datasource.username=${postgres.username}",
            "spring.datasource.password=${postgres.password}",
        ).applyTo(context.environment)
    }

    companion object {
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:14"))
            .withDatabaseName("test_db")
            .withUsername("test-db-user")
            .withPassword("test-db-password")
    }
}