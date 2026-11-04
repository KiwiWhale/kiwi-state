package io.github.kiwiwhale.kiwistate.exception

class StateMachineAlreadyRegisteredException(
    name: String
) : StateMachineException(
    "State machine '$name' is already registered."
)
