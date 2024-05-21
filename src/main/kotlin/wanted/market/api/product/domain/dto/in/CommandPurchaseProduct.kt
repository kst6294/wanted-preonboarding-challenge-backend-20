package wanted.market.api.product.domain.dto.`in`

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "상품구매")
data class CommandPurchaseProduct(

    @Schema(description = "상품 아이디", example = "1")
    val productId: Long,

    @Schema(description = "구매 수량", example = "1")
    val quantity: Int
)
