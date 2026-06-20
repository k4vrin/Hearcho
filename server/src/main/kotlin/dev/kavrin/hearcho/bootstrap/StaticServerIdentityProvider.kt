package dev.kavrin.hearcho.bootstrap

import dev.kavrin.hearcho.diagnostic.application.port.ServerIdentityProvider

class StaticServerIdentityProvider(
    private val name: String,
) : ServerIdentityProvider {
    override fun serverName(): String = name
}
