package wanted.market.api.product.domain.dto.`in`

import io.swagger.v3.oas.annotations.media.Schema
import wanted.market.api.product.domain.entity.ProductStatus

@Schema(title = "상품 등록")
data class CommandRegisterProduct(
    @Schema(description = "상품명", example = "맥북 프로")
    val name: String,

    @Schema(description = "가격", example = "15000")
    val price: Long,

    @Schema(description = "수량", example = "1")
    val quantity: Int,

    @Schema(description = "상태", example = "SALE")
    val status: ProductStatus
)
