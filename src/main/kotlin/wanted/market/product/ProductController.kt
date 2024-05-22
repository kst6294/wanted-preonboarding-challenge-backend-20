package wanted.market.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(@Autowired private val productService: ProductService) {

    @GetMapping
    fun findAllProducts(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok().body(productService.findAllProducts())
    }

    @GetMapping("/{id}")
    fun findProduct(@PathVariable id: Long): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok().body(productService.findProduct(id))
    }

}