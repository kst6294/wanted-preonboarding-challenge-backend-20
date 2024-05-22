package wanted.market.api.order.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.order.domain.entity.OrderStatus.SALEAPPROVAL
import wanted.market.api.order.repository.OrderRepository
import wanted.market.api.product.domain.dto.out.CommandApproveOrderResult

@Service
@Transactional
class OrderService(
    private val orderRepository: OrderRepository
) {
    fun approveOrder(orderId: Long, memberId: Long) : CommandApproveOrderResult {
        val order = orderRepository.findByIdAndSellerId(orderId, memberId)
            ?: throw IllegalArgumentException("주문을 찾을 수 없습니다.")
        order.approveOrder()
        return CommandApproveOrderResult.from(order)
    }

    fun confirmOrder(orderId: Long, memberId: Long): Long {
        val order = orderRepository.findByIdAndBuyerIdAndOrderStatus(orderId, memberId, SALEAPPROVAL)
            ?: throw IllegalArgumentException("주문을 찾을 수 없습니다.")
        order.confirmOrder()
        return order.id!!
    }
}