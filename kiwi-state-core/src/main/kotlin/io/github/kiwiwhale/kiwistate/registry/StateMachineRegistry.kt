package io.github.kiwiwhale.kiwistate.registry

import io.github.kiwiwhale.kiwistate.api.StateMachine
import io.github.kiwiwhale.kiwistate.exception.StateMachineAlreadyRegisteredException
import java.util.concurrent.ConcurrentHashMap

/**
 * Registry for managing state machine instances.
 *
 * Supports both String and typed StateMachineKey identifiers.
 */
class StateMachineRegistry {

    private val machines =
        ConcurrentHashMap<String, StateMachine<*, *, *>>()

    /**
     * Registers a state machine using a String identifier.
     */
    fun <S : Any, E : Any, C> register(
        name: String,
        machine: StateMachine<S, E, C>
    ) {
        require(name.isNotBlank()) {
            "State machine name must not be blank."
        }

        val previous = machines.putIfAbsent(name, machine)

        if (previous != null) {
            throw StateMachineAlreadyRegisteredException(name)
        }
    }

    /**
     * Registers a state machine using a typed key.
     */
    fun <S : Any, E : Any, C> register(
        key: StateMachineKey<S, E, C>,
        machine: StateMachine<S, E, C>
    ) {
        register(key.name, machine)
    }

    /**
     * Retrieves a state machine using a String identifier.
     *
     * The caller must provide the correct generic types.
     */
    @Suppress("UNCHECKED_CAST")
    fun <S : Any, E : Any, C> get(
        name: String
    ): StateMachine<S, E, C>? {
        return machines[name] as? StateMachine<S, E, C>
    }

    /**
     * Retrieves a state machine using a typed key.
     */
    fun <S : Any, E : Any, C> get(
        key: StateMachineKey<S, E, C>
    ): StateMachine<S, E, C>? {
        return get(key.name)
    }

    /**
     * Removes a registered state machine.
     */
    fun unregister(name: String): Boolean {
        return machines.remove(name) != null
    }

    /**
     * Removes a registered state machine using a typed key.
     */
    fun unregister(key: StateMachineKey<*, *, *>): Boolean {
        return unregister(key.name)
    }

    /**
     * Checks whether a state machine is registered.
     */
    fun contains(name: String): Boolean {
        return machines.containsKey(name)
    }

    /**
     * Checks whether a state machine is registered.
     */
    fun contains(key: StateMachineKey<*, *, *>): Boolean {
        return contains(key.name)
    }

    /**
     * Returns all registered state machine names.
     */
    fun names(): Set<String> {
        return machines.keys.toSet()
    }

    /**
     * Returns the number of registered state machines.
     */
    val size: Int
        get() = machines.size
}
