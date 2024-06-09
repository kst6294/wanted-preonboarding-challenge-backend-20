package com.api.jellomarket.service.product

import com.api.jellomarket.domain.product.Product
import com.api.jellomarket.domain.product.ProductRepository
import com.api.jellomarket.domain.user.User
import com.api.jellomarket.dto.product.ProductPurchaseRequestDTO
import com.api.jellomarket.enums.error.ErrorCodeCustom
import com.api.jellomarket.enums.product.ProductState
import com.api.jellomarket.exception.BusinessException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class ProductPurchaseServiceTest @Autowired constructor(
    private val productPurchaseService: ProductPurchaseService,
    private val productRepository: ProductRepository,
) {
    // 상품 구매 테스트
    @Test
    @DisplayName("[실패] 상품 구매 테스트 : 비회원 (throw)")
    @Throws(BusinessException::class)
    fun purchaseProductFailThrow() {
        // given
        val savedProduct = productRepository.save(
            Product(productId = 1, name = "test", price = 1000, state = ProductState.ON_SALE, stock = 0, imageUrl = "", sellerId = 1, createdAt = "2024-10-10 12:00:00"))
        val productPurchaseRequestDTO = ProductPurchaseRequestDTO(1, 1000)

        //when, then
        assertThrows(BusinessException::class.java) {
            productPurchaseService.purchaseProduct(null, savedProduct.productId!!, productPurchaseRequestDTO)
        }
    }

    // 상품 구매 테스트
    @Test
    @DisplayName("[실패] 상품 구매 테스트 : 비회원 (message)")
    fun purchaseProductFailMessage() {
        // given
        val savedProduct = productRepository.save(
            Product(productId = 1, name = "test", price = 1000, state = ProductState.ON_SALE, stock = 0, imageUrl = "", sellerId = 1, createdAt = "2024-10-10 12:00:00"))
        val productPurchaseRequestDTO = ProductPurchaseRequestDTO(1, 1000)

        //when
        try {
            productPurchaseService.purchaseProduct(null, savedProduct.productId!!, productPurchaseRequestDTO)
        } catch (e: BusinessException) {
            // then
            assertEquals(ErrorCodeCustom.NEED_SIGN_UP.message, e.errorMessage)
        }
    }

    // 상품 구매 테스트
    @Test
    @DisplayName("[실패] 상품 구매 테스트 : 재고없고 모두 구매확정")
    fun purchaseProduct() {
        // given
        val user = User(userId = 1L, email = "1", password = "2", name = "3", phoneNumber = "4")
        val savedProduct = productRepository.save(
            Product(productId = 1, name = "test", price = 1000, state = ProductState.ON_SALE, stock = 0, imageUrl = "", sellerId = 1, createdAt = "2024-10-10 12:00:00"))
        val productPurchaseRequestDTO = ProductPurchaseRequestDTO(1, 1000)

        // when
        val result = productPurchaseService.purchaseProduct(user, savedProduct.productId!!, productPurchaseRequestDTO)

        // then
        assertNotNull(result)
    }
}
