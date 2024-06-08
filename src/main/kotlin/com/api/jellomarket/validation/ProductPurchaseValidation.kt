package com.api.jellomarket.validation

import com.api.jellomarket.domain.product.Product
import com.api.jellomarket.domain.product.ProductRepository
import com.api.jellomarket.domain.user.User
import com.api.jellomarket.enums.error.ErrorCodeCustom
import com.api.jellomarket.enums.product.ProductState
import com.api.jellomarket.exception.BusinessException

class ProductPurchaseValidation(
    private val productRepository: ProductRepository
) {
    fun validateProductPurchase(user: User?, productId: Long): Product {
        // check user
        if (user == null) {
            throw BusinessException.of(ErrorCodeCustom.NEED_SIGN_UP)
        }

        val targetProductOptional = productRepository.findById(productId)
        if (targetProductOptional.isEmpty) {
            throw BusinessException.of(ErrorCodeCustom.INVALID_PRODUCT)
        }
        val product = targetProductOptional.get()
        when (product.state) {
            ProductState.ON_SALE, ProductState.ON_RESERVED -> {
                return product
            }
            ProductState.SOLD_OUT -> {
                throw BusinessException.of(ErrorCodeCustom.SOLD_OUT)
            }
            ProductState.DELETED -> {
                throw BusinessException.of(ErrorCodeCustom.INVALID_PURCHASE)
            }
        }
    }
}