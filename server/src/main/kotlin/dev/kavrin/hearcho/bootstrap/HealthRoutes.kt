package dev.kavrin.hearcho.bootstrap

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureHealthRoutes() {
    routing {
        get("/health/live") {
            call.respond(
                HttpStatusCode.OK,
                "",
            )
        }
    }
}
