package io.github.kiwiwhale.kiwistate.registry

/**
 * Typed identifier for a state machine.
 *
 * @param S State type.
 * @param E Event type.
 * @param C Context type.
 * @param name Unique state machine name.
 */
data class StateMachineKey<S : Any, E : Any, C>(
    val name: String
) {

    init {
        require(name.isNotBlank()) {
            "State machine key must not be blank."
        }
    }
}
