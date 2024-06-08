package com.api.jellomarket.service.product

import com.api.jellomarket.domain.product.Product
import com.api.jellomarket.domain.product.ProductRepository
import com.api.jellomarket.domain.user.User
import com.api.jellomarket.domain.user.UserRepository
import com.api.jellomarket.dto.product.ProductCreateRequestDTO
import com.api.jellomarket.enums.product.ProductState
import jakarta.transaction.Transactional
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class ProductServiceTest @Autowired constructor(
    val userRepository: UserRepository,
    val productService: ProductService,
    val productRepository: ProductRepository
) {

    @AfterEach
    fun tearDown() {
        productRepository.deleteAll()
    }

    @Test
    @DisplayName("상품 목록 조회")
    fun getProductList() {
        // given
        productRepository.saveAll(
            listOf(
                Product(
                    name = "test1",
                    price = 1000,
                    state = ProductState.ON_SALE,
                    stock = 10,
                    imageUrl = "test",
                    category = "test",
                    sellerId = 1,
                    createdAt = "2021-10-10"
                ),
                Product(
                    name = "test2",
                    price = 2000,
                    state = ProductState.ON_SALE,
                    stock = 20,
                    imageUrl = "test",
                    category = "test",
                    sellerId = 2,
                    createdAt = "2021-10-10"
                ),
                Product(
                    name = "test3",
                    price = 3000,
                    state = ProductState.ON_SALE,
                    stock = 30,
                    imageUrl = "test",
                    category = "test",
                    sellerId = 3,
                    createdAt = "2021-10-10"
                )
            )
        ) // 상품 3개 저장
        // when
        val productList = productService.getProductList()

        // then
        assertEquals(3, productList.size)
        assertEquals("test1", productList[0].name)
        assertEquals(1000, productList[0].price)
        assertEquals(ProductState.ON_SALE, productList[0].state)
    }

    @Test
    @DisplayName("상품 목록 조회 : 존재하지 않는 상품 (throw)")
    @Throws(Exception::class)
    fun getProductDetailNotFound() {
        // given
        val productId = 1L // 존재하지 않는 상품 ID

        // when

        // then
        assertThrows(IllegalArgumentException::class.java) {
            productService.getProductDetail(productId)
        }
    }

    @Test
    @DisplayName("상품 상세 조회 : 존재하지 않는 상품 (errorMessage)")
    fun getProductDetailNotFoundErrorMessage() {
        // given
        val productId = 1L // 존재하지 않는 상품 ID

        // when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            productService.getProductDetail(productId)
        }

        // then
        assertEquals("상품이 존재하지 않습니다.", exception.message)
    }

    @Test
    @DisplayName("상품 상세 조회 : 1개")
    fun getProductDetail() {
        // given
        val product = productRepository.save(
            Product(
                name = "test1",
                price = 1000,
                state = ProductState.ON_SALE,
                stock = 10,
                imageUrl = "test",
                category = "test",
                sellerId = 1,
                createdAt = "2021-10-10"
            )
        ) // 상품 1개 저장

        // when
        val productDetail = productService.getProductDetail(product.id!!)

        // then
        assertEquals("test1", productDetail.name)
        assertEquals(1000, productDetail.price)
        assertEquals(ProductState.ON_SALE.toString(), productDetail.state)
    }

    @Test
    @DisplayName("[실패] 상품 등록 : 비로그인 유저 (throw)")
    @Throws(Exception::class)
    fun saveProductInvalidUser() {
        // given
        val request = ProductCreateRequestDTO(
            name = "test",
            price = 1000,
            state = ProductState.ON_SALE,
            stock = 10,
            createdAt = "2021-10-10"
        )

        // when
        // then
        assertThrows(IllegalArgumentException::class.java) {
            productService.saveProduct(null, request)
        }
    }

    @Test
    @DisplayName("[실패] 상품 등록 : 필수값 미입력 (errorMesasage)")
    fun saveProductInvalidNameErrorMessage() {
        // given
        val user = userRepository.save(
            User(
                email = "abc@test.com",
                password = "123",
                name = "test1",
                phoneNumber = "123123"
            )
        )
        val request = ProductCreateRequestDTO(
            name = "",
            price = 1000,
            state = ProductState.ON_SALE,
            stock = 10,
            createdAt = "2021-10-10"
        )

        // when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            productService.saveProduct(user, request)
        }

        // then
        assertEquals("상품명을 입력해주세요.", exception.message)
    }

    @Test
    @DisplayName("[실패] 상품 등록 : 비로그인 유저 (errorMessage)")
    fun saveProductInvalidPriceErrorMessage() {
        // given
        val request = ProductCreateRequestDTO(
            name = "test",
            price = 10,
            state = ProductState.ON_SALE,
            stock = 10,
            createdAt = "2021-10-10"
        )

        // when
        val exception = assertThrows(IllegalArgumentException::class.java) {
            productService.saveProduct(null, request)
        }

        // then
        assertEquals("로그인이 필요합니다.", exception.message)
    }

    @Test
    @DisplayName("[성공] 상품 등록")
    fun saveProduct() {
        // given
        val user = userRepository.save(
            User(
                email = "test@test.com",
                password = "123",
                name = "test1",
                phoneNumber = "123123"
            )
        )
        val request = ProductCreateRequestDTO(
            name = "test",
            price = 1000,
            state = ProductState.ON_SALE,
            stock = 10,
            createdAt = "2021-10-10"
        )

        // when
        val savedProductName = productService.saveProduct(user, request)

        // then
        assertEquals("test", savedProductName)
        assertEquals(1, productRepository.count())
        assertEquals(user.userId, productRepository.findAll()[0].sellerId)
    }
}