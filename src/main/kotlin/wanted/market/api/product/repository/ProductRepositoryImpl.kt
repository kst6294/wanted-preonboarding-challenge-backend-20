package wanted.market.api.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import wanted.market.api.order.domain.entity.QOrder.order
import wanted.market.api.product.domain.entity.ProductStatus
import wanted.market.api.product.domain.entity.QProduct.product

@Repository
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductRepositoryCustom {

    override fun isPurchasable(productId: Long, buyerId: Long): Long {
        return queryFactory
            .select(order.count())
            .from(order).innerJoin(order.orderItems)
            .where(order.buyer.id.eq(buyerId), order.orderItems.any().product.id.eq(productId))
            .fetchOne() ?: 0
    }

    override fun findByIdAndStatus(productIds: List<Long?>, status: ProductStatus) : Long {
        return queryFactory.select(product.count())
            .from(product)
            .where(product.id.`in`(productIds), product.status.eq(status))
            .fetchOne() ?: 0
    }

    override fun updateProduct(productIds: List<Long?>) {
        queryFactory.update(product)
            .set(product.status, ProductStatus.SOLD_OUT)
            .where(product.id.`in`(productIds))
            .execute()
    }
}