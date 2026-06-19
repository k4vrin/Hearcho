package dev.kavrin.hearcho

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedLogicCommonTest {
    @Test
    fun flowCanBeTestedWithTurbine() =
        runTest {
            flowOf("loading", "ready").test {
                assertEquals("loading", awaitItem())
                assertEquals("ready", awaitItem())
                awaitComplete()
            }
        }
}
