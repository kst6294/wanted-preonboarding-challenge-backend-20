package wanted.market.order.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wanted.market.order.entity.Order

@Repository
interface OrderRepository : JpaRepository<Order, Long> {

}