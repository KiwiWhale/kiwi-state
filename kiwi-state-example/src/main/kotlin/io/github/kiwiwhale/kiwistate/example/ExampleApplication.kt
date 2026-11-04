package io.github.kiwiwhale.kiwistate.example

import io.github.kiwiwhale.kiwistate.example.key.runKeyRegistryExample
import io.github.kiwiwhale.kiwistate.example.key.runStringRegistryExample
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    runKeyRegistryExample()

    println()

    runStringRegistryExample()
}