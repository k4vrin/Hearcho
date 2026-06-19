package dev.kavrin.hearcho

class Greeting {
    private val platform = getPlatform()

    fun greet(): String = sayHello(platform.name)
}
