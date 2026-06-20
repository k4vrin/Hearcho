package dev.kavrin.hearcho.diagnostic.application

import dev.kavrin.hearcho.testfixture.FakeServerIdentityProvider
import kotlin.test.Test
import kotlin.test.assertEquals

class GetServerDiagnosticTest {
    @Test
    fun `returns server diagnostic from identity provider`() {
        val useCase =
            GetServerDiagnostic(
                serverIdentityProvider = FakeServerIdentityProvider("test-server"),
            )

        val diagnostic = useCase.execute()

        assertEquals("test-server", diagnostic.name)
    }
}
