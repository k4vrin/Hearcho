package dev.kavrin.hearcho.diagnostic.adapter.inbound.http

import dev.kavrin.hearcho.diagnostic.application.GetServerDiagnostic
import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureDiagnosticRoutes(getServerDiagnostic: GetServerDiagnostic) {
    routing {
        get("/diagnostic/server") {
            val diagnostic = getServerDiagnostic.execute()
            call.respondText(diagnostic.name)
        }
    }
}
