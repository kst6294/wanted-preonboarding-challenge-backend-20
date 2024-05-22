package wanted.market.api.order.repository

import com.querydsl.core.types.dsl.BooleanExpression
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

    override fun findAllPurchaseHistory(
        productId: Long,
        memberId: Long
    ): List<RetrievePurchaseHistoryResult> {
        val results = queryFactory
            .select(order)
            .from(order)
            .leftJoin(order.orderItems, orderItem).fetchJoin()
            .where(
                buyerIdEq(memberId),
                productIdEq(productId)
            )
            .fetch()
        return results.map { RetrievePurchaseHistoryResult.from(it) }
    }

    override fun findAllReservationHistory(
        productId: Long,
        memberId: Long
    ): List<RetrieveReservationHistoryResult> {
        val results = queryFactory
            .select(order)
            .from(order)
            .leftJoin(order.orderItems, orderItem).fetchJoin()
            .where(
                sellerIdIn(memberId),
                buyerIdIn(memberId),
                productIdEq(productId)
            )
            .fetch()
        return results.map { RetrieveReservationHistoryResult.from(it) }
    }

    override fun findOneOrder(orderId: Long, memberId: Long, orderStatus: OrderStatus): Order? {
        return queryFactory
            .selectDistinct(order)
            .from(order)
            .leftJoin(order.orderItems).fetchJoin()
            .where(
                orderIdEq(orderId),
                buyerIdEq(memberId),
                orderStatusEq(orderStatus)
            )
            .fetchOne()
    }

    private fun buyerIdIn(memberId: Long): BooleanExpression? {
        return order.buyer.id.`in`(memberId)
    }

    private fun sellerIdIn(memberId: Long): BooleanExpression? {
        return order.seller.id.`in`(memberId)
    }

    private fun productIdEq(productId: Long): BooleanExpression? {
        return orderItem.product.id.eq(productId)
    }

    private fun orderStatusEq(orderStatus: OrderStatus): BooleanExpression? {
        return order.orderStatus.eq(orderStatus)
    }

    private fun orderIdEq(orderId: Long): BooleanExpression? {
        return order.id.eq(orderId)
    }

    private fun buyerIdEq(memberId: Long): BooleanExpression? {
        return order.buyer.id.eq(memberId)
    }
}
