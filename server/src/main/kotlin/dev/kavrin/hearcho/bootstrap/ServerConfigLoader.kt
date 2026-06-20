package dev.kavrin.hearcho.bootstrap

import javax.naming.ConfigurationException

object ServerConfigLoader {
    fun load(env: Map<String, String> = System.getenv()): ServerConfig =
        ServerConfig(
            host = env.required("HEARCHO_SERVER_HOST"),
            port =
                env.required("HEARCHO_SERVER_PORT").toIntOrNull()
                    ?: throw ConfigurationException("HEARCHO_SERVER_PORT must be a valid integer"),
            environment = env.required("HEARCHO_ENV").toAppEnvironment(),
        )

    private fun Map<String, String>.required(name: String): String =
        this[name]?.takeIf { it.isNotBlank() }
            ?: throw ConfigurationException("Missing required environment variable: $name")

    private fun String.toAppEnvironment(): AppEnvironment =
        when (uppercase()) {
            "LOCAL" -> AppEnvironment.LOCAL
            "TEST" -> AppEnvironment.TEST
            "PRODUCTION" -> AppEnvironment.PRODUCTION
            else -> throw ConfigurationException("Invalid HEARCHO_ENV: $this")
        }
}
