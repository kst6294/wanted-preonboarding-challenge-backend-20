package wanted.market.api.domain.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import wanted.market.api.domain.product.service.ProductService
import wanted.market.api.domain.product.dto.RegisterProductRequest
import wanted.market.api.domain.product.dto.ProductInfo

@RestController
@RequestMapping("/products")
class ProductController(
    @Autowired private val productService: ProductService
) { 
    @GetMapping
    fun findAllProducts(): ResponseEntity<List<ProductInfo>> {
      return ResponseEntity.ok().body(productService.findAllProducts())
    }

    @GetMapping("/{productId}")
    fun findProduct(@PathVariable productId: Long): ResponseEntity<ProductInfo> {
        return ResponseEntity.ok().body(productService.findProduct(productId))
    }

    @PostMapping
    fun registerProduct(@RequestBody registerProductRequest: RegisterProductRequest): ResponseEntity<Void> {
        productService.registerProduct(registerProductRequest)
        return ResponseEntity.ok().build()
    }
}
