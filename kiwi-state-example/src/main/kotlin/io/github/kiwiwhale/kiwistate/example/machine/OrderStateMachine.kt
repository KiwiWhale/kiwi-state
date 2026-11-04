package io.github.kiwiwhale.kiwistate.example.machine

import io.github.kiwiwhale.kiwistate.example.model.Order
import io.github.kiwiwhale.kiwistate.example.model.OrderEvent
import io.github.kiwiwhale.kiwistate.example.model.OrderState
import io.github.kiwiwhale.kiwistate.kiwiState

fun createOrderStateMachine() =
    kiwiState<OrderState, OrderEvent, Order> {

        initialState(OrderState.PENDING)

        state(OrderState.PENDING) {

            on<OrderEvent.PaymentCompleted>(
                to = OrderState.PAID
            ) {
                guard { _, order ->
                    !order.paid
                }

                action { _, order ->
                    order.paid = true
                }
            }

            on<OrderEvent.Cancel>(
                to = OrderState.CANCELLED
            )
        }

        state(OrderState.PAID) {
            on<OrderEvent.ShippingStarted>(
                to = OrderState.SHIPPING
            )
        }

        state(OrderState.SHIPPING) {
            on<OrderEvent.DeliveryCompleted>(
                to = OrderState.COMPLETED
            )
        }

        terminalState(OrderState.COMPLETED)
        terminalState(OrderState.CANCELLED)
    }
