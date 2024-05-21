package wanted.market.api.product.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.product.application.ProductService
import wanted.market.api.product.domain.dto.out.RetrieveProductResult

@RestController
@RequestMapping("/products")
@Tag(name = "04. 상품 API", description = "인증/권한 없이 접근할 수 있는 상품 API")
class ProductController(
    private val productService: ProductService
) {

    @Operation(
        summary = "상품 전체 조회", responses = [
            ApiResponse(responseCode = "200", description = "모든 상품 조회 성공")
        ]
    )
    @GetMapping
    fun findAllProducts() : ApiResultResponse<List<RetrieveProductResult>> {
        return ApiResultResponse(
            data = productService.findAllProducts()
        )
    }

    @Operation(
        summary = "상품 상세 조회", responses = [
            ApiResponse(responseCode = "400", description = "상품이 존재하지 않음"),
            ApiResponse(responseCode = "200", description = "상품 조회 성공")
        ]
    )
    @GetMapping("/{productId}")
    fun findOneProduct(@PathVariable productId: Long) : ApiResultResponse<RetrieveProductResult> {
        return ApiResultResponse(
            data = productService.findOneProduct(productId)
        )
    }
}