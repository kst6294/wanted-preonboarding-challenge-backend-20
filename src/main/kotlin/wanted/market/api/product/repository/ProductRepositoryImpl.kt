package wanted.market.api.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import wanted.market.api.order.domain.entity.QOrder.order

@Repository
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductRepositoryCustom {
    override fun isPurchasable(productId: Long, buyerId: Long): Long {
        return queryFactory
            .select(order.count())
            .from(order).innerJoin(order.orderItems)
            .where(order.buyer.id.eq(buyerId), order.orderItems.any().id.eq(productId))
            .fetchOne() ?: 0
    }
}