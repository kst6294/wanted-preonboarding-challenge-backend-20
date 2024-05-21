package wanted.market.api.product.domain.dto.out

import wanted.market.api.product.domain.entity.Product

data class CommandPurchaseProductResult(
    val id: Long,
    val name: String,
    val price: Long,
    val quantity: Int
) {
    companion object {
        fun from(product: Product, quantity: Int): CommandPurchaseProductResult {
            return CommandPurchaseProductResult(
                id = product.id!!,
                name = product.name,
                price = product.price,
                quantity = quantity
            )
        }
    }
}
