package dev.kavrin.hearcho.bootstrap

import javax.naming.ConfigurationException
import kotlin.test.Test
import kotlin.test.assertFailsWith

class ServerConfigTest {
    @Test
    fun `loads typed config from environment map`() {
        val config =
            ServerConfigLoader.load(
                env =
                    mapOf(
                        "HEARCHO_SERVER_HOST" to "127.0.0.1",
                        "HEARCHO_SERVER_PORT" to "8080",
                        "HEARCHO_ENV" to "TEST",
                    ),
            )

        kotlin.test.assertEquals("127.0.0.1", config.host)
        kotlin.test.assertEquals(8080, config.port)
        kotlin.test.assertEquals(AppEnvironment.TEST, config.environment)
    }

    @Test
    fun `fails when required config is missing`() {
        assertFailsWith<ConfigurationException> {
            ServerConfigLoader.load(emptyMap())
        }
    }

    @Test
    fun `fails when port is invalid`() {
        assertFailsWith<ConfigurationException> {
            ServerConfigLoader.load(
                env =
                    mapOf(
                        "HEARCHO_SERVER_HOST" to "127.0.0.1",
                        "HEARCHO_SERVER_PORT" to "oops",
                        "HEARCHO_ENV" to "LOCAL",
                    ),
            )
        }
    }
}
