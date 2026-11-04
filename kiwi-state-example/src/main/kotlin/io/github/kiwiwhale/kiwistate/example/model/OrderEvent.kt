package io.github.kiwiwhale.kiwistate.example.model

sealed interface OrderEvent {

    data object PaymentCompleted : OrderEvent

    data object ShippingStarted : OrderEvent

    data object DeliveryCompleted : OrderEvent

    data object Cancel : OrderEvent
}
