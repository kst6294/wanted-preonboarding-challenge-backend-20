package wanted.market.product.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import wanted.market.common.exception.ErrorCode
import wanted.market.member.entity.Member
import wanted.market.member.service.MemberService
import wanted.market.order.entity.Order
import wanted.market.order.repository.OrderRepository
import wanted.market.product.entity.Product
import wanted.market.product.entity.ProductStatus
import wanted.market.product.exception.ProductException
import wanted.market.product.repository.ProductRepository
import java.util.*



class ProductServiceTest: BehaviorSpec({

    val productRepository = mockk<ProductRepository>(relaxed = true)
    val memberService = mockk<MemberService>(relaxed = true)
    val orderRepository = mockk<OrderRepository>(relaxed = true)
    val productService = ProductService(productRepository, memberService, orderRepository)

    val memberIdA = 1L
    val memberIdB = 2L
    val memberIdC = 3L
    val productIdA = 1L
    val productIdB = 2L
    val productReservedId = 3L
    val orderIdA = 1L
    val orderIdB = 2L

    val memberA = Member("멤버A", "seller@example.com", memberIdA)
    val memberB = Member("멤버B", "buyer@example.com", memberIdB)

    val productBySellerA = Product("product", 1000, memberA, productIdA)
    val productByBuyerA = Product("product", 1000, memberB, productIdB)
    val productReserved = Product("product", 1000, memberB, productReservedId).apply{
        productStatus = ProductStatus.RESERVED
    }

    val orderByA = Order(memberA, memberB, productByBuyerA, orderIdA)
    val orderByB = Order(memberB, memberA, productBySellerA, orderIdB)


    given("내가 판매자일 때") {
        every { productRepository.findById(productIdA) } returns Optional.of(productBySellerA)
        every { orderRepository.findByBuyerIdOrSellerIdAndProductId(memberIdA, memberIdA, productIdA) } returns listOf(orderByB)
        When ("상품 상세 조회를 하면") {
            val productDetail = productService.findProductDetails(memberIdA, productIdA)
            then("거래내역이 보인다. (상품 판매자는 본인)") {
                productDetail.orderHistory.sellerName shouldBe memberA.name
                productDetail.orderHistory.buyerName shouldBe memberB.name
            }
        }
    }

    given ("내가 구매자일 때") {
        every { productRepository.findById(productIdB) } returns Optional.of(productByBuyerA)
        every { orderRepository.findByBuyerIdOrSellerIdAndProductId(memberIdA, memberIdA, productIdB) } returns listOf(orderByA)
        When("상품 상세 조회를 하면"){
            val productDetail = productService.findProductDetails(memberIdA, productIdB)
            then("거래내역이 보인다. (상품 구매자는 본인)") {
                productDetail.orderHistory.sellerName shouldBe memberB.name
                productDetail.orderHistory.buyerName shouldBe memberA.name
            }
        }
    }

    given ("구매자/판매자가 아닐 때") {
        every { productRepository.findById(productIdA) } returns Optional.of(productByBuyerA)
        every { orderRepository.findByBuyerIdOrSellerIdAndProductId(memberIdC, memberIdC, productIdA) } returns emptyList()
        When("상품 상세 조회를 하면"){
            val productDetail = productService.findProductDetails(memberIdC, productIdA)
            then("거래 내역이 보이지 않는다.") {
                productDetail.orderHistory.buyerName shouldBe ""
            }
        }
    }

    given("존재하지 않은 id로"){
        val invalidId = -1L
        every { productRepository.findById(invalidId) } returns Optional.empty()
        every { orderRepository.findByBuyerIdOrSellerIdAndProductId(memberIdA, memberIdA, invalidId) } returns emptyList()
        When("상품 상세 조회를 하면"){
            then("Exception을 던진다."){
                val exception = shouldThrow<ProductException> { productService.findProductDetails(memberIdA, invalidId) }
                exception.errorCode shouldBe ErrorCode.PRODUCT_NOT_FOUND
            }
        }
    }

    given("판매 중이 아닌 상품 id로"){
        every { productRepository.findById(productReserved.id) } returns Optional.of(productReserved)
        When("판매 중 상품을 조회하면"){
            then("Exception을 던진다."){
                val exception = shouldThrow<ProductException> { productService.findProductOnSale(productReserved.id) }
                exception.errorCode shouldBe ErrorCode.PRODUCT_NOT_FOR_SALE
            }
        }
    }
})