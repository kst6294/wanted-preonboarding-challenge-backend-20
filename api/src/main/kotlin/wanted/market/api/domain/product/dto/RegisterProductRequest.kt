package wanted.market.api.domain.product.dto

data class RegisterProductRequest(
    var name: String,
    var price: Int,
    var sellerId: Long
)