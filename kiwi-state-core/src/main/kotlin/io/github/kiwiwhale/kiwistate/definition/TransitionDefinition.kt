package io.github.kiwiwhale.kiwistate.definition

import kotlin.reflect.KClass

internal data class TransitionDefinition<S : Any, E : Any, C>(
    val from: S,
    val to: S,
    val eventType: KClass<out E>,
    val guard: suspend (E, C) -> Boolean,
    val action: suspend (E, C) -> Unit
)
