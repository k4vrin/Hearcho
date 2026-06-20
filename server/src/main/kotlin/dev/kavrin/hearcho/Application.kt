package dev.kavrin.hearcho

import dev.kavrin.hearcho.bootstrap.ServerConfig
import dev.kavrin.hearcho.bootstrap.ServerConfigLoader
import dev.kavrin.hearcho.bootstrap.configureServer
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val config = ServerConfigLoader.load()
    embeddedServer(
        Netty,
        port = config.port,
        host = config.host,
        module = {
            module(config)
        },
    ).start(wait = true)
}

fun Application.module(config: ServerConfig = ServerConfigLoader.load()) {
    configureServer(config)
}
