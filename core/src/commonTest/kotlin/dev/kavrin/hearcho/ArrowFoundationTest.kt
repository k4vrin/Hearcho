package dev.kavrin.hearcho

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nonEmptyListOf
import arrow.core.raise.Raise
import arrow.core.raise.either
import kotlin.test.Test
import kotlin.test.assertEquals

class ArrowFoundationTest {
    @Test
    fun eitherAndRaiseExpressAProjectOwnedFailure() {
        val result: Either<ArrowFoundationFailure, Int> =
            either {
                requirePositive(2)
            }

        assertEquals(Either.Right(2), result)
    }

    @Test
    fun nonEmptyListPreservesAtLeastOneValidationFailure() {
        val failures: NonEmptyList<ArrowFoundationFailure> =
            nonEmptyListOf(
                ArrowFoundationFailure.InvalidValue,
                ArrowFoundationFailure.InvalidValue,
            )

        assertEquals(2, failures.size)
    }

    @Test
    fun generatedOpticUpdatesAnImmutableModel() {
        val original = ArrowOpticsSmoke(value = "before")

        val updated = ArrowOpticsSmoke.value.set(original, "after")

        assertEquals("after", updated.value)
        assertEquals("before", original.value)
    }

    private fun Raise<ArrowFoundationFailure>.requirePositive(value: Int): Int =
        if (value > 0) {
            value
        } else {
            raise(ArrowFoundationFailure.InvalidValue)
        }
}

private sealed interface ArrowFoundationFailure {
    data object InvalidValue : ArrowFoundationFailure
}
