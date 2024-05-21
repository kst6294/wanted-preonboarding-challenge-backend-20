package wanted.market.api.product.domain.dto.out

import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderStatus

data class CommandApproveOrderResult(
    val orderId: Long,
    val orderStatus: OrderStatus
) {
    companion object {
        fun from(order: Order) : CommandApproveOrderResult {
            return CommandApproveOrderResult(
                orderId = order.id!!,
                orderStatus = order.orderStatus
            )
        }
    }
}
