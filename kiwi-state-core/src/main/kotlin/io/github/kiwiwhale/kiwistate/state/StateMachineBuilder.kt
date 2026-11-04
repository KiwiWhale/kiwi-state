package io.github.kiwiwhale.kiwistate.state

import io.github.kiwiwhale.kiwistate.KiwiStateDsl
import io.github.kiwiwhale.kiwistate.api.StateMachine
import io.github.kiwiwhale.kiwistate.definition.StateMachineDefinition
import io.github.kiwiwhale.kiwistate.definition.TransitionDefinition
import io.github.kiwiwhale.kiwistate.exception.InvalidStateMachineDefinitionException

/**
 * Builder for defining a state machine.
 */
@KiwiStateDsl
class StateMachineBuilder<S : Any, E : Any, C> {

    private var initialState: S? = null

    private val states = mutableSetOf<S>()

    private val transitions =
        mutableListOf<TransitionDefinition<S, E, C>>()

    /**
     * Defines the initial state.
     */
    fun initialState(state: S) {
        initialState = state
        states += state
    }

    /**
     * Defines a state and its transitions.
     */
    fun state(
        state: S,
        block: StateBuilder<S, E, C>.() -> Unit
    ) {
        states += state

        StateBuilder(
            state = state,
            transitions = transitions
        ).apply(block)
    }

    /**
     * Defines a terminal state.
     *
     * A terminal state cannot have outgoing transitions.
     */
    fun terminalState(state: S) {
        states += state
    }

    /**
     * Builds the state machine.
     */
    fun build(): StateMachine<S, E, C> {
        val initial = initialState
            ?: throw InvalidStateMachineDefinitionException(
                "Initial state must be defined."
            )

        validate()

        return StateMachine(
            definition = StateMachineDefinition(
                initialState = initial,
                states = states.toSet(),
                transitions = transitions.toList()
            )
        )
    }

    private fun validate() {
        transitions.forEach { transition ->

            if (transition.to !in states) {
                throw InvalidStateMachineDefinitionException(
                    "Target state '${transition.to}' is not defined."
                )
            }
        }

        val duplicates = transitions
            .groupBy { it.from to it.eventType }
            .filterValues { it.size > 1 }

        if (duplicates.isNotEmpty()) {
            throw InvalidStateMachineDefinitionException(
                "Duplicate transitions detected."
            )
        }
    }
}
