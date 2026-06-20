package dev.kavrin.hearcho.testfixture

import dev.kavrin.hearcho.diagnostic.application.port.ServerIdentityProvider

class FakeServerIdentityProvider(
    private val name: String,
) : ServerIdentityProvider {
    override fun serverName(): String = name
}
