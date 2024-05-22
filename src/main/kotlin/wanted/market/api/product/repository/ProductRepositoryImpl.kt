package wanted.market.api.product.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import wanted.market.api.order.domain.entity.QOrder.order
import wanted.market.api.order.domain.entity.QOrderItem.orderItem
import wanted.market.api.product.domain.entity.ProductStatus
import wanted.market.api.product.domain.entity.QProduct.product

@Repository
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductRepositoryCustom {

    override fun isPurchasable(productId: Long, buyerId: Long): Long {
        return queryFactory
            .select(order.count())
            .from(order).innerJoin(order.orderItems, orderItem)
            .where(buyerIdEq(buyerId), productIdEq(productId))
            .fetchOne() ?: 0
    }

    override fun findByIdAndStatus(productIds: List<Long?>, status: ProductStatus) : Long {
        return queryFactory
            .select(product.count())
            .from(product)
            .where(productIdIn(productIds), productStatusEq(status))
            .fetchOne() ?: 0
    }

    override fun updateProduct(productIds: List<Long?>) {
        queryFactory.update(product)
            .set(product.status, ProductStatus.SOLD_OUT)
            .where(productIdIn(productIds))
            .execute()
    }

    private fun productStatusEq(status: ProductStatus): BooleanExpression? =
        product.status.eq(status)

    private fun productIdIn(productIds: List<Long?>): BooleanExpression? =
        product.id.`in`(productIds)

    private fun productIdEq(productId: Long): BooleanExpression? =
        orderItem.product.id.eq(productId)

    private fun buyerIdEq(buyerId: Long): BooleanExpression? =
        order.buyer.id.eq(buyerId)
}