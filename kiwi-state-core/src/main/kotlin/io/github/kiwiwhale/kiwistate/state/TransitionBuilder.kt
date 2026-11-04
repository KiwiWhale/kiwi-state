package io.github.kiwiwhale.kiwistate.state

import io.github.kiwiwhale.kiwistate.KiwiStateDsl

/**
 * Builder for transition conditions and actions.
 */
@KiwiStateDsl
class TransitionBuilder<E : Any, C> {

    internal var guard: suspend (E, C) -> Boolean =
        { _, _ -> true }

    internal var action: suspend (E, C) -> Unit =
        { _, _ -> }

    /**
     * Defines a guard condition.
     */
    fun guard(
        block: suspend (E, C) -> Boolean
    ) {
        guard = block
    }

    /**
     * Defines an action executed during transition.
     */
    fun action(
        block: suspend (E, C) -> Unit
    ) {
        action = block
    }
}
