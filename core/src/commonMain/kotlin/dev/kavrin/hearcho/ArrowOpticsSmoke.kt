package dev.kavrin.hearcho

import arrow.optics.optics

@optics
internal data class ArrowOpticsSmoke(
    val value: String,
) {
    companion object
}
