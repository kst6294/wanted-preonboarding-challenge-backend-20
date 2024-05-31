package wanted.market.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wanted.market.order.entity.Order
import wanted.market.order.entity.OrderStatus
import java.util.Optional

@Repository
interface OrderRepository : JpaRepository<Order, Long> {

    //n+1 문제 해결하기
    fun findByBuyerIdAndOrderStatus(buyerId: Long, orderStatus: OrderStatus) : List<Order>

    fun findByBuyerIdAndProductId(buyerId: Long, productId: Long) : Optional<Order>
    fun findBySellerIdAndOrderStatus(sellerId: Long, orderStatus: OrderStatus): List<Order>

}