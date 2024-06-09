package com.api.jellomarket.domain.order

import com.api.jellomarket.enums.order.OrderStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class OrderRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : OrderRepositoryCustom
{
    override fun countByOrderStatus(orderStatus: OrderStatus): Long {
        return queryFactory.select(QOrder.order)
            .from(QOrder.order)
            .where(QOrder.order.status.eq(orderStatus))
            .fetch().size.toLong()
    }
}