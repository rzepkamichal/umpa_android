package pl.mrz.umpa.util

/**
 * Enables to split data into two representations - one for its input and the other for its output.
 */
data class TwoWayData<T>(
    val input: T,
    val output: T
)