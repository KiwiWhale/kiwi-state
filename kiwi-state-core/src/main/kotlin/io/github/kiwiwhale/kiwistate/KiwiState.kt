package io.github.kiwiwhale.kiwistate

import io.github.kiwiwhale.kiwistate.api.StateMachine
import io.github.kiwiwhale.kiwistate.registry.StateMachineRegistry
import io.github.kiwiwhale.kiwistate.state.StateMachineBuilder

/**
 * Entry point for defining KiwiState state machines.
 *
 * Provides Kotlin DSL functions for creating typed state machines.
 */
object KiwiState {

    /**
     * Creates a state machine using a Kotlin DSL.
     *
     * @param S State type.
     * @param E Event type.
     * @param C Context type.
     * @param block State machine definition.
     * @return Configured state machine.
     */
    fun <S : Any, E : Any, C> stateMachine(
        block: StateMachineBuilder<S, E, C>.() -> Unit
    ): StateMachine<S, E, C> =
        StateMachineBuilder<S, E, C>()
            .apply(block)
            .build()

    /**
     * Creates a new state machine registry.
     */
    fun registry(): StateMachineRegistry =
        StateMachineRegistry()
}

/**
 * Creates a state machine using the Kotlin DSL.
 *
 * This is a convenient top-level alternative to
 * [KiwiState.stateMachine].
 *
 * @param S State type.
 * @param E Event type.
 * @param C Context type.
 * @param block State machine definition.
 * @return Configured state machine.
 */
fun <S : Any, E : Any, C> kiwiState(
    block: StateMachineBuilder<S, E, C>.() -> Unit
): StateMachine<S, E, C> = KiwiState.stateMachine(block)

