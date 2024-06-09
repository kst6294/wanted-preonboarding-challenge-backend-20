package com.api.jellomarket.controller

import com.api.jellomarket.config.ResponseCustom
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
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
    val productService: ProductService,
    val productPurchaseService: ProductPurchaseService
) {
    @GetMapping(PRODUCT_LIST)
    fun getProductList(): ResponseEntity<ResponseCustom> =
        ResponseEntity.ok(ResponseCustom.Success(productService.getProductList()))

    @GetMapping(PRODUCT_DETAIL)
    fun getProductDetail(productId: Long): ResponseEntity<ResponseCustom> =
        ResponseEntity.ok(ResponseCustom.Success(data = productService.getProductDetail(productId)))

    @PostMapping(PRODUCT)
    fun saveProduct(
        @LoginUser user: User,
        @RequestBody request: ProductCreateRequestDTO
    ): ResponseEntity<ResponseCustom> =
        ResponseEntity.ok(ResponseCustom.Success(data = productService.saveProduct(user, request)))

    @PostMapping(PRODUCT_PURCHASE)
    fun purchaseProduct(
        @LoginUser user: User,
        @PathVariable productId: Long,
        @RequestBody request: ProductPurchaseRequestDTO
    ): ResponseEntity<ResponseCustom> =
        ResponseEntity.ok(
            ResponseCustom.Success(
                data = productPurchaseService.purchaseProduct(
                    user,
                    productId,
                    request
                )
            )
        )
}