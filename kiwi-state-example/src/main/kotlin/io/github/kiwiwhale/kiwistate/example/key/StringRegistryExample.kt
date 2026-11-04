package io.github.kiwiwhale.kiwistate.example.key

import io.github.kiwiwhale.kiwistate.example.machine.createOrderStateMachine
import io.github.kiwiwhale.kiwistate.example.model.Order
import io.github.kiwiwhale.kiwistate.example.model.OrderEvent
import io.github.kiwiwhale.kiwistate.example.model.OrderState
import io.github.kiwiwhale.kiwistate.registry.StateMachineRegistry

/**
 * Example using String identifiers.
 */
suspend fun runStringRegistryExample() {

    val registry = StateMachineRegistry()

    // Create state machine.
    val orderMachine = createOrderStateMachine()

    // Register using String.
    registry.register(
        name = "order",
        machine = orderMachine
    )

    // Retrieve using String.
    val machine = registry.get<
            OrderState,
            OrderEvent,
            Order
            >("order") ?: error("Order state machine not found")

    // Create order.
    val order = Order(
        id = "ORDER-STRING-001"
    )

    println("=== String Registry Example ===")
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
