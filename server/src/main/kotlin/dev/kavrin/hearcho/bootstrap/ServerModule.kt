package dev.kavrin.hearcho.bootstrap

import dev.kavrin.hearcho.diagnostic.adapter.inbound.http.configureDiagnosticRoutes
import dev.kavrin.hearcho.diagnostic.application.GetServerDiagnostic
import io.ktor.server.application.Application

/**
 * Installs server routes and wires feature dependencies at server composition root.
 */
fun Application.configureServer(config: ServerConfig = ServerConfigLoader.load()) {
    configureHealthRoutes()

    val serverIdentityProvider =
        StaticServerIdentityProvider(
            name = "hearcho-server-${config.environment.name.lowercase()}",
        )
    val getServerDiagnostic =
        GetServerDiagnostic(
            serverIdentityProvider = serverIdentityProvider,
        )

    configureDiagnosticRoutes(
        getServerDiagnostic = getServerDiagnostic,
    )
}
