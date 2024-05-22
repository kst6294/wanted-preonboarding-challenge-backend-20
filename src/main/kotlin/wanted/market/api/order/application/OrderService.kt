package wanted.market.api.order.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderStatus
import wanted.market.api.order.domain.entity.OrderStatus.NONE
import wanted.market.api.order.domain.entity.OrderStatus.SALEAPPROVAL
import wanted.market.api.order.repository.OrderRepository
import wanted.market.api.product.domain.dto.out.CommandApproveOrderResult
import wanted.market.api.product.domain.entity.ProductStatus.RESERVATION
import wanted.market.api.product.repository.ProductRepository

@Service
@Transactional
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) {

    fun approveOrder(orderId: Long, sellerId: Long) : CommandApproveOrderResult {
        val order = findOrderById(orderId, sellerId, NONE)
        order.approveOrder()
        return CommandApproveOrderResult.from(order)
    }

    fun confirmOrder(orderId: Long, buyerId: Long): Long {
        val order = findOrderById(orderId, buyerId, SALEAPPROVAL)

        order.confirmOrder()

        val productIds = order.orderItems.map { it.product.id }
        val count = productRepository.findByIdAndStatus(productIds, RESERVATION)

        if (productIds.count() == count.toInt()) {
            productRepository.updateProduct(productIds)
        }

        return order.id!!
    }

    private fun findOrderById(orderId: Long, memberId: Long, orderStatus: OrderStatus) : Order {
        return orderRepository.findOneOrder(orderId, memberId, orderStatus) ?: throw IllegalArgumentException("주문을 찾을 수 없습니다.")
    }
}