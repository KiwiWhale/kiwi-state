package io.github.kiwiwhale.kiwistate.example.key

import io.github.kiwiwhale.kiwistate.example.machine.createOrderStateMachine
import io.github.kiwiwhale.kiwistate.example.model.Order
import io.github.kiwiwhale.kiwistate.example.model.OrderEvent
import io.github.kiwiwhale.kiwistate.example.model.OrderState
import io.github.kiwiwhale.kiwistate.registry.StateMachineKey
import io.github.kiwiwhale.kiwistate.registry.StateMachineRegistry

/**
 * Example using typed StateMachineKey.
 */
suspend fun runKeyRegistryExample() {

    val registry = StateMachineRegistry()

    // Create a typed key.
    val orderKey = StateMachineKey<
            OrderState,
            OrderEvent,
            Order
            >("order")

    // Create state machine.
    val orderMachine = createOrderStateMachine()

    // Register.
    registry.register(
        key = orderKey,
        machine = orderMachine
    )

    // Retrieve.
    val machine = registry.get(orderKey)
        ?: error("Order state machine not found")

    // Create order.
    val order = Order(
        id = "ORDER-KEY-001"
    )

    println("=== Typed Key Example ===")
    println("Initial State: ${machine.currentState}")

    // Payment completed.
    val paymentResult = machine.send(
        event = OrderEvent.PaymentCompleted,
        context = order
    )

    println("Payment Result: $paymentResult")
    println("Current State: ${machine.currentState}")
    println("Order Paid: ${order.paid}")

    // Shipping started.
    val shippingResult = machine.send(
        event = OrderEvent.ShippingStarted,
        context = order
    )

    println("Shipping Result: $shippingResult")
    println("Current State: ${machine.currentState}")

    // Delivery completed.
    val deliveryResult = machine.send(
        event = OrderEvent.DeliveryCompleted,
        context = order
    )

    println("Delivery Result: $deliveryResult")
    println("Final State: ${machine.currentState}")
}
