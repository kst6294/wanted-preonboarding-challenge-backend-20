package com.api.jellomarket.controller

import com.api.jellomarket.config.auth.LoginUser
import com.api.jellomarket.config.path.*
import com.api.jellomarket.domain.product.Product
import com.api.jellomarket.domain.user.User
import com.api.jellomarket.dto.product.ProductCreateRequestDTO
import com.api.jellomarket.dto.product.ProductDetailDTO
import com.api.jellomarket.dto.product.ProductListDTO
import com.api.jellomarket.dto.product.ProductPurchaseRequestDTO
import com.api.jellomarket.service.product.ProductPurchaseService
import com.api.jellomarket.service.product.ProductService
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
    val productService: ProductService,
    val productPurchaseService: ProductPurchaseService
) {
    @GetMapping(PRODUCT_LIST)
    fun getProductList(): List<ProductListDTO> {
        val productList = productService.getProductList()
        return productList
    }

    @GetMapping(PRODUCT_DETAIL)
    fun getProductDetail(productId: Long): ProductDetailDTO {
        val product = productService.getProductDetail(productId)
        return product
    }

    @PostMapping(PRODUCT)
    fun saveProduct(@LoginUser user: User, @RequestBody request: ProductCreateRequestDTO): String {
        val savedProductName = productService.saveProduct(user, request)
        return savedProductName
    }

    @PostMapping(PRODUCT_PURCHASE)
    fun purchaseProduct(@LoginUser user: User, @PathVariable productId: Long, @RequestBody request: ProductPurchaseRequestDTO): Boolean {
        val purchaseProductName = productPurchaseService.purchaseProduct(user, productId, request)
        return true
    }
}