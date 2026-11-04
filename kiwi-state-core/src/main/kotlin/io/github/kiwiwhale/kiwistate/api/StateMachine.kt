package io.github.kiwiwhale.kiwistate.api

import io.github.kiwiwhale.kiwistate.definition.StateMachineDefinition
import io.github.kiwiwhale.kiwistate.exception.AmbiguousTransitionException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Executes a state machine.
 */
class StateMachine<S : Any, E : Any, C> internal constructor(
    private val definition: StateMachineDefinition<S, E, C>
) {

    private val mutex = Mutex()

    @Volatile
    private var state: S = definition.initialState

    /**
     * Current state.
     */
    val currentState: S
        get() = state

    /**
     * All states defined in this machine.
     */
    val states: Set<S>
        get() = definition.states

    /**
     * Sends an event to the state machine.
     */
    suspend fun send(
        event: E,
        context: C
    ): TransitionResult<S> = mutex.withLock {

        val candidates = definition.transitions.filter { transition ->
            transition.from == state &&
                    transition.eventType.isInstance(event)
        }

        if (candidates.isEmpty()) {
            return@withLock TransitionResult.NoTransition
        }

        val accepted = candidates.filter { transition ->
            transition.guard(event, context)
        }

        if (accepted.isEmpty()) {
            return@withLock TransitionResult.Rejected
        }

        if (accepted.size > 1) {
            throw AmbiguousTransitionException(
                "Multiple transitions matched event: ${event::class}"
            )
        }

        val transition = accepted.single()
        val previousState = state

        transition.action(event, context)

        state = transition.to

        TransitionResult.Transitioned(
            from = previousState,
            to = transition.to
        )
    }
}
