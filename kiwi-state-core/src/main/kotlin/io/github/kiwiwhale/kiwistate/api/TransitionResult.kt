package io.github.kiwiwhale.kiwistate.api

sealed interface TransitionResult<out S> {

    data class Transitioned<S>(
        val from: S,
        val to: S
    ) : TransitionResult<S>

    data object Rejected : TransitionResult<Nothing>

    data object NoTransition : TransitionResult<Nothing>
}
