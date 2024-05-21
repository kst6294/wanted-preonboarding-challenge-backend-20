package wanted.market.api.product.repository

interface ProductRepositoryCustom {
    fun isPurchasable(productId: Long, buyerId: Long) : Long
}