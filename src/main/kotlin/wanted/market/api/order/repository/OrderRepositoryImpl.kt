package wanted.market.api.order.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import wanted.market.api.order.domain.dto.out.RetrievePurchaseHistoryResult
import wanted.market.api.order.domain.dto.out.RetrieveReservationHistoryResult
import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderStatus
import wanted.market.api.order.domain.entity.QOrder.order
import wanted.market.api.order.domain.entity.QOrderItem.orderItem

@Repository
class OrderRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : OrderRepositoryCustom {

    override fun findAllPurchaseHistory(productId: Long, memberId: Long) : List<RetrievePurchaseHistoryResult> {
        val results = queryFactory
            .select(order)
            .from(order)
            .leftJoin(order.orderItems, orderItem).fetchJoin()
            .where(order.buyer.id.eq(memberId), orderItem.product.id.eq(productId))
            .fetch()
        return results.map { RetrievePurchaseHistoryResult.from(it) }
    }

    override fun findAllReservationHistory(productId: Long, memberId: Long): List<RetrieveReservationHistoryResult> {
        val results = queryFactory
            .select(order)
            .from(order)
            .leftJoin(order.orderItems, orderItem).fetchJoin()
            .where(order.seller.id.`in`(memberId), order.buyer.id.`in`(memberId), orderItem.product.id.eq(productId))
            .fetch()
        return results.map { RetrieveReservationHistoryResult.from(it) }
    }

    override fun findOneOrder(orderId: Long, memberId: Long, orderStatus: OrderStatus): Order? {
        return queryFactory
            .selectDistinct(order)
            .from(order)
            .leftJoin(order.orderItems).fetchJoin()
            .where(order.orderItems.any().product.id.eq(orderId), order.buyer.id.eq(memberId))
            .fetchOne()
    }
}