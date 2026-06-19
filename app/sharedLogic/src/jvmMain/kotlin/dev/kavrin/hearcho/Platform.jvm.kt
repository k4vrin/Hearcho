@file:Suppress("MatchingDeclarationName")

package dev.kavrin.hearcho

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()
