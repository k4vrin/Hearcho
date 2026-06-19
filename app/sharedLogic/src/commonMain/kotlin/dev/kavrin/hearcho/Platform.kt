package dev.kavrin.hearcho

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform