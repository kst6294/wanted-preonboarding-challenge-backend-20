package wanted.market.api.product.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import wanted.market.api.common.ApiResultResponse
import wanted.market.api.product.application.ProductService
import wanted.market.api.product.domain.dto.out.RetrieveProductResult

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun findAllProducts() : ApiResultResponse<List<RetrieveProductResult>> {
        return ApiResultResponse(
            data = productService.findAllProducts()
        )
    }

    @GetMapping("/{productId}")
    fun findOneProduct(@PathVariable productId: Long) : ApiResultResponse<RetrieveProductResult> {
        return ApiResultResponse(
            data = productService.findOneProduct(productId)
        )
    }
}