package wanted.market.api.product.domain.dto.out

data class CommandPurchaseProductResult(
    val productId: Long,
    val quantity: Int
) {
    companion object {
        fun from(productId: Long, quantity: Int): CommandPurchaseProductResult {
            return CommandPurchaseProductResult(productId = productId, quantity = quantity)
        }
    }
}
