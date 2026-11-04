package io.github.kiwiwhale.kiwistate.exception

open class StateMachineException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)
