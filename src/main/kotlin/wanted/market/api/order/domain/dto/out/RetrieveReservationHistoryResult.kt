package wanted.market.api.order.domain.dto.out

import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderItem
import wanted.market.api.order.domain.entity.OrderStatus

data class RetrieveReservationHistoryResult(
    val orderId: Long,
    val orderStatus: OrderStatus,
    val orderItems: MutableList<RetrievePurchaseOrderItemsResult> = mutableListOf()
) {
    companion object {
        fun from(order: Order) : RetrieveReservationHistoryResult {
            return RetrieveReservationHistoryResult(
                orderId = order.id!!,
                orderStatus = order.orderStatus,
                orderItems = order.orderItems.map { RetrievePurchaseOrderItemsResult.from(it) }.toMutableList()
            )
        }
    }

    data class RetrievePurchaseOrderItemsResult(
        val orderId: Long,
        val productName: String,
        val price: Long,
        val quantity: Int
    ) {
        companion object {
            fun from(orderItem: OrderItem) : RetrievePurchaseOrderItemsResult {
                return RetrievePurchaseOrderItemsResult(
                    orderId = orderItem.id!!,
                    productName = orderItem.productName,
                    price = orderItem.price,
                    quantity = orderItem.quantity
                )
            }
        }
    }
}
