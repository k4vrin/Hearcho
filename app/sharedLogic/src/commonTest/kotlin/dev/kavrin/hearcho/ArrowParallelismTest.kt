package dev.kavrin.hearcho

import arrow.fx.coroutines.parZip
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrowParallelismTest {
    @Test
    fun parZipCombinesIndependentSuspendingOperations() =
        runTest {
            val result =
                parZip(
                    { 20 },
                    { 22 },
                ) { left, right ->
                    left + right
                }

            assertEquals(42, result)
        }
}
