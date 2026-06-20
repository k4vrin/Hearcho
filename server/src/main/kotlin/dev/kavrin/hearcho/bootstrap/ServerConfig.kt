package dev.kavrin.hearcho.bootstrap

data class ServerConfig(
    val host: String,
    val port: Int,
    val environment: AppEnvironment,
)

enum class AppEnvironment {
    LOCAL,
    TEST,
    PRODUCTION,
}
