package wanted.market.api.order.repository

import wanted.market.api.order.domain.dto.out.RetrievePurchaseHistoryResult
import wanted.market.api.order.domain.dto.out.RetrieveReservationHistoryResult

interface OrderRepositoryCustom {
    fun findAllPurchaseHistory(productId: Long, memberId: Long) : List<RetrievePurchaseHistoryResult>
    fun findAllReservationHistory(productId: Long, memberId: Long): List<RetrieveReservationHistoryResult>
}