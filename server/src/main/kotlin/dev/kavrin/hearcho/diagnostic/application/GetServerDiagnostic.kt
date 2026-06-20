package dev.kavrin.hearcho.diagnostic.application

import dev.kavrin.hearcho.diagnostic.application.port.ServerIdentityProvider
import dev.kavrin.hearcho.diagnostic.domain.ServerDiagnostic

class GetServerDiagnostic(
    private val serverIdentityProvider: ServerIdentityProvider,
) {
    fun execute(): ServerDiagnostic =
        ServerDiagnostic(
            name = serverIdentityProvider.serverName(),
        )
}
