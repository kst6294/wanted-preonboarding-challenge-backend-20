package wanted.market.api.product.domain.dto.out

data class CommandRegisterProductResult(
    val id: Long,
    val name: String,
    val price: Long,
    val quantity: Int,
    val status: String
)
