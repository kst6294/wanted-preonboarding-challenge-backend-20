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
    /**
     * 상품 구매 유효성 검사
     * @param user
     * @param productId
     * @return Product
     * @throws BusinessException
     * 가입 유저 확인
     * 상품 존재 확인
     * 상품 상태 확인
     * 상품 가격 변동 확인
     */
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
                // 구매 가능한 경우 요청 시점과 현재 상품 가격 확인
                if (product.price != productRepository.findById(productId).get().price) {
                    throw BusinessException.of(ErrorCodeCustom.PRODUCT_PRICE_DIFF)
                }
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