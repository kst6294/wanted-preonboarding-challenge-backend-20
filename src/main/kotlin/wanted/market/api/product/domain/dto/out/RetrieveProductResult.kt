package wanted.market.api.product.domain.dto.out

import wanted.market.api.product.domain.entity.Product
import wanted.market.api.product.domain.entity.ProductStatus

data class RetrieveProductResult(
    val id: Long,
    val name: String,
    val price: Long,
    val quantity: Int,
    val status: ProductStatus
) {
    companion object {
        fun from(products: Product): RetrieveProductResult {
            return RetrieveProductResult(
                id = products.id!!,
                name = products.name,
                price = products.price,
                quantity = products.quantity,
                status = products.status
            )
        }
    }
}