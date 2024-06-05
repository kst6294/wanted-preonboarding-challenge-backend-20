package wanted.market.product.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import wanted.market.product.dto.ProductDetailResponse
import wanted.market.product.dto.ProductResponse
import wanted.market.product.service.ProductService
import wanted.market.product.dto.SaveProductRequest

@RestController
@RequestMapping("/products")
class ProductController(@Autowired private val productService: ProductService) {

    @GetMapping
    fun findAllProducts(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok().body(productService.findAllProducts())
    }

    @GetMapping("/{productId}")
    fun findProductDetails(@RequestParam memberId: Long, @PathVariable productId: Long): ResponseEntity<ProductDetailResponse> {
        return ResponseEntity.ok().body(productService.findProductDetails(memberId, productId))
    }

    @PostMapping
    fun saveProduct(@RequestBody saveProductRequest: SaveProductRequest): ResponseEntity<Void> {
        productService.saveProduct(saveProductRequest)
        return ResponseEntity.ok().build()
    }


    //내가 구매한 용품
    @GetMapping("/orders")
    fun findMyOrderProduct(@RequestParam memberId: Long): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok().body(productService.findMyOrderProduct(memberId))
    }

    //예약중인 용품 (내가 구매자)
    @GetMapping("/reserve/buyer")
    fun findReserveProductByBuyer(@RequestParam buyerId: Long): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok().body(productService.findReserveProductByBuyer(buyerId))
    }

    //예약중인 용품 (내가 판매자)
    @GetMapping("/reserve/seller")
    fun findReserveProductBySeller(@RequestParam sellerId: Long): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok().body(productService.findReserveProductBySeller(sellerId))
    }

}