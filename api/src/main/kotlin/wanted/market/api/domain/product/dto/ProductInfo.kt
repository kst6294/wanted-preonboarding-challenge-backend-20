package wanted.market.api.domain.product.dto

import wanted.market.api.domain.product.entity.Product


data class ProductInfo(
    var id: Long,
    var name: String,
    var price: Int,
    var status: String,
    var sellerId: Long
) {
    companion object {
        fun from(product: Product): ProductInfo {
            return ProductInfo(
                id = product.id!!,
                sellerId = product.seller.id!!,
                name = product.name,
                price = product.price,
                status = product.status.value
            )
        }
    }
}
