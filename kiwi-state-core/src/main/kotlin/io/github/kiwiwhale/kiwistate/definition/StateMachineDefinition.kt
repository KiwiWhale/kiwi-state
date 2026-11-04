package io.github.kiwiwhale.kiwistate.definition

internal data class StateMachineDefinition<S : Any, E : Any, C>(
    val initialState: S,
    val states: Set<S>,
    val transitions: List<TransitionDefinition<S, E, C>>
)
