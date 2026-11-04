package io.github.kiwiwhale.kiwistate.state

import io.github.kiwiwhale.kiwistate.KiwiStateDsl
import io.github.kiwiwhale.kiwistate.definition.TransitionDefinition
import kotlin.reflect.KClass

/**
 * Builder for state-specific transitions.
 */
@KiwiStateDsl
class StateBuilder<S : Any, E : Any, C> internal constructor(
    private val state: S,
    private val transitions: MutableList<TransitionDefinition<S, E, C>>
) {

    /**
     * Defines a transition for an event.
     */
    inline fun <reified T : E> on(
        to: S,
        noinline block: TransitionBuilder<T, C>.() -> Unit = {}
    ) {
        on(
            eventType = T::class,
            to = to,
            block = block
        )
    }

    /**
     * Defines a transition using an explicit event type.
     */
    fun <T : E> on(
        eventType: KClass<T>,
        to: S,
        block: TransitionBuilder<T, C>.() -> Unit = {}
    ) {
        val builder = TransitionBuilder<T, C>()
            .apply(block)

        transitions += TransitionDefinition(
            from = state,
            to = to,
            eventType = eventType,
            guard = { event, context ->
                builder.guard(eventType.java.cast(event), context)
            },
            action = { event, context ->
                builder.action(eventType.java.cast(event), context)
            }
        )
    }
}