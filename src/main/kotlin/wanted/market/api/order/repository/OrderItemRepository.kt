package wanted.market.api.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import wanted.market.api.order.domain.entity.OrderItem

interface OrderItemRepository : JpaRepository<OrderItem, Long>{
}