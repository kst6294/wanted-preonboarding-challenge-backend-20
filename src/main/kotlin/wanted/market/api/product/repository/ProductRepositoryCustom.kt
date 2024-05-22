package wanted.market.api.product.repository

import wanted.market.api.product.domain.entity.ProductStatus

interface ProductRepositoryCustom {
    fun isPurchasable(productId: Long, buyerId: Long): Long
    fun findByIdAndStatus(productIds: List<Long?>, status: ProductStatus): Long
    fun updateProduct(productIds: List<Long?>)
}