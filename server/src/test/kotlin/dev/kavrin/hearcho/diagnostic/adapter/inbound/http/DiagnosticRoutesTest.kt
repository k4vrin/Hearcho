package dev.kavrin.hearcho.diagnostic.adapter.inbound.http

import dev.kavrin.hearcho.diagnostic.application.GetServerDiagnostic
import dev.kavrin.hearcho.testfixture.FakeServerIdentityProvider
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class DiagnosticRoutesTest {
    @Test
    fun `diagnostic route is wired to use case`() =
        testApplication {
            application {
                configureDiagnosticRoutes(
                    getServerDiagnostic =
                        GetServerDiagnostic(
                            serverIdentityProvider = FakeServerIdentityProvider("route-test-server"),
                        ),
                )
            }

            val response = client.get("/diagnostic/server")

            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals("route-test-server", response.bodyAsText())
        }
}
