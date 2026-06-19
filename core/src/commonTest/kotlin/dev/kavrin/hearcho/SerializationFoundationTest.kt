package dev.kavrin.hearcho

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializationFoundationTest {
    @Test
    fun serializableModelRoundTripsThroughJson() {
        val model = FoundationModel(value = "ready")

        val encoded = Json.encodeToString(model)
        val decoded = Json.decodeFromString<FoundationModel>(encoded)

        assertEquals(model, decoded)
    }
}

@Serializable
private data class FoundationModel(
    val value: String,
)
