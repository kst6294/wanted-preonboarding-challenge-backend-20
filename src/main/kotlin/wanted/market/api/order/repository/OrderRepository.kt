package wanted.market.api.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderStatus

interface OrderRepository: JpaRepository<Order, Long>, OrderRepositoryCustom {
    fun findByIdAndSellerId(orderId: Long, memberId: Long): Order?
    fun findByIdAndBuyerIdAndOrderStatus(orderId: Long, memberId: Long, orderStatus: OrderStatus): Order?
}