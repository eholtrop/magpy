package com.dpal.libs.optional

sealed class Optional<T> {

    data class Value<T>(
        val value: T
    ) : Optional<T>()

    data class Error<T>(
        val throwable: Throwable
    ) : Optional<T>()
}

fun <T : Any> T.optional(): Optional<T> = Optional.Value(this)

fun <T> Throwable.error(): Optional<T> = Optional.Error(this)
