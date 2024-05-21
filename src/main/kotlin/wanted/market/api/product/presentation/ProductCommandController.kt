package wanted.market.api.product.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.common.security.CustomMemberDetails
import wanted.market.api.product.application.ProductCommandService
import wanted.market.api.product.domain.dto.`in`.CommandPurchaseProduct
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct
import wanted.market.api.product.domain.dto.out.CommandPurchaseProductResult
import wanted.market.api.product.domain.dto.out.CommandRegisterProductResult

@RestController
@RequestMapping("/products")
@Tag(name = "03. 상품 API", description = "인증 또는 권한 부여 후에 접근할 수 있는 상품 API")
class ProductCommandController(
    private val productCommandService: ProductCommandService
) {

    @Operation(
        summary = "상품 등록", responses = [
            ApiResponse(responseCode = "400", description = "회원이 존재하지 않습니다."),
            ApiResponse(responseCode = "200", description = "상품 등록 성공")
        ]
    )
    @PostMapping
    fun registerProduct(@RequestBody request: CommandRegisterProduct,
                        @AuthenticationPrincipal member: CustomMemberDetails): ApiResultResponse<CommandRegisterProductResult> {
        return ApiResultResponse(
            data = productCommandService.registerProduct(request, member.getMemberId())
        )
    }

    @Operation(
        summary = "상품 구매", responses = [
            ApiResponse(responseCode = "400(1)", description = "회원이 존재하지 않습니다."),
            ApiResponse(responseCode = "400(2)", description = "상품이 존재하지 않습니다."),
            ApiResponse(responseCode = "400(3)", description = "이미 구매한 상품입니다."),
            ApiResponse(responseCode = "200", description = "상품 구매 성공")
        ]
    )
    @PostMapping("/purchase")
    fun purchaseProduct(@RequestBody request: CommandPurchaseProduct,
                        @AuthenticationPrincipal member: CustomMemberDetails): ApiResultResponse<CommandPurchaseProductResult> {
        return ApiResultResponse(
            data = productCommandService.purchaseProduct(request, member.getMemberId())
        )
    }
}