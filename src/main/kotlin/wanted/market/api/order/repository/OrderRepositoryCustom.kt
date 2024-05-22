package wanted.market.api.order.repository

import wanted.market.api.order.domain.dto.out.RetrievePurchaseHistoryResult
import wanted.market.api.order.domain.dto.out.RetrieveReservationHistoryResult
import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderStatus

interface OrderRepositoryCustom {
    fun findAllPurchaseHistory(productId: Long, memberId: Long) : List<RetrievePurchaseHistoryResult>
    fun findAllReservationHistory(productId: Long, memberId: Long): List<RetrieveReservationHistoryResult>
    fun findOneOrder(orderId: Long, memberId: Long, orderStatus: OrderStatus): Order?
}