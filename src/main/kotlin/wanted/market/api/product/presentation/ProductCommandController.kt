package wanted.market.api.product.presentation

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.product.application.ProductCommandService
import wanted.market.api.product.domain.dto.`in`.CommandPurchaseProduct
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct
import wanted.market.api.product.domain.dto.out.CommandPurchaseProductResult
import wanted.market.api.product.domain.dto.out.CommandRegisterProductResult

@RestController
@RequestMapping("/products")
class ProductCommandController(
    private val productCommandService: ProductCommandService
) {

    @PostMapping
    fun registerProduct(@RequestBody request: CommandRegisterProduct): ApiResultResponse<CommandRegisterProductResult> {
        return ApiResultResponse(
            data = productCommandService.registerProduct(request)
        )
    }

    @PostMapping("/purchase")
    fun purchaseProduct(@RequestBody request: CommandPurchaseProduct) : ApiResultResponse<CommandPurchaseProductResult> {
        return ApiResultResponse(
            data = productCommandService.purchaseProduct(request)
        )
    }
}