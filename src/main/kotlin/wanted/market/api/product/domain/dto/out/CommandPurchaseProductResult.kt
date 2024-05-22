package wanted.market.api.product.domain.dto.out

import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderStatus
import wanted.market.api.product.domain.entity.Product

data class CommandPurchaseProductResult(
    val orderId: Long,
    val sellerId: Long,
    val buyerId: Long,
    val orderStatus: OrderStatus,
    val productId: Long,
    val name: String,
    val price: Long,
    val quantity: Int
) {
    companion object {
        fun from(order: Order, product: Product, quantity: Int): CommandPurchaseProductResult {
            return CommandPurchaseProductResult(
                orderId = order.id!!,
                sellerId = product.seller.id!!,
                buyerId = order.buyer.id!!,
                orderStatus = order.orderStatus,
                productId = product.id!!,
                name = product.name,
                price = product.price,
                quantity = quantity
            )
        }
    }
}
