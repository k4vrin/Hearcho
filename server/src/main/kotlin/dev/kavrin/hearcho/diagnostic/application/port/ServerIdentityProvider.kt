package dev.kavrin.hearcho.diagnostic.application.port

interface ServerIdentityProvider {
    fun serverName(): String
}
