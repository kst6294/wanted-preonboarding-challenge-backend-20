package wanted.market.api.product.domain.dto.`in`

import wanted.market.api.product.domain.entity.ProductStatus

data class CommandRegisterProduct(
    val name: String,
    val price: Long,
    val stockQuantity: Int,
    val status: ProductStatus
)
