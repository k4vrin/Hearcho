package dev.kavrin.hearcho.bootstrap

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class HealthRoutesTest {
    @Test
    fun `live health endpoint returns ok`() =
        testApplication {
            application {
                configureServer(
                    config =
                        ServerConfig(
                            host = "127.0.0.1",
                            port = 0,
                            environment = AppEnvironment.TEST,
                        ),
                )
            }

            val response = client.get("/health/live")

            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals("", response.body<String>())
        }
}
