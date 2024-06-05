package wanted.market.product.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import jakarta.transaction.Transactional
import org.springframework.boot.test.context.SpringBootTest
import wanted.market.common.exception.ErrorCode
import wanted.market.member.entity.Member
import wanted.market.member.service.MemberService
import wanted.market.order.entity.Order
import wanted.market.order.repository.OrderRepository
import wanted.market.product.entity.Product
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
    val orderIdA = 1L
    val orderIdB = 2L

    val memberA = Member("멤버A", "seller@example.com", memberIdA)
    val memberB = Member("멤버B", "buyer@example.com", memberIdB)
    val memberC = Member("멤버C", "buyer@example.com", memberIdC)

    val productBySellerA = Product("product", 1000, memberA, productIdA)
    val productByBuyerA = Product("product", 1000, memberB, productIdB)
    val orderByA = Order(memberA, memberB, productByBuyerA, orderIdA)
    val orderByB = Order(memberB, memberA, productBySellerA, orderIdB)


    given("상품 상세 조회 테스트") {

        When ("내가 판매자라면") {
            every { productRepository.findById(productIdA) } returns Optional.of(productBySellerA)
            every { orderRepository.findByBuyerIdOrSellerIdAndProductId(memberIdA, memberIdA, productIdA) } returns listOf(orderByB)

            then("거래내역이 보인다. (상품 판매자는 본인)") {
                val productDetail = productService.findProductDetails(memberIdA, productIdA)
                productDetail.orderHistory.sellerName shouldBe memberA.name
                productDetail.orderHistory.buyerName shouldBe memberB.name
            }
        }

        When ("내가 구매자라면") {
            every { productRepository.findById(productIdB) } returns Optional.of(productByBuyerA)
            every { orderRepository.findByBuyerIdOrSellerIdAndProductId(memberIdA, memberIdA, productIdB) } returns listOf(orderByA)
            then("거래내역이 보인다. (상품 구매자는 본인)") {
                val productDetail = productService.findProductDetails(memberIdA, productIdB)
                productDetail.orderHistory.sellerName shouldBe memberB.name
                productDetail.orderHistory.buyerName shouldBe memberA.name
            }
        }

        When ("구매자/판매자가 아니라면") {
            every { productRepository.findById(productIdA) } returns Optional.of(productByBuyerA)
            every { orderRepository.findByBuyerIdOrSellerIdAndProductId(memberIdC, memberIdC, productIdA) } returns emptyList()

            then("거래 내역이 보이지 않는다.") {
                val productDetail = productService.findProductDetails(memberIdC, productIdA)
                productDetail.orderHistory.buyerName shouldBe ""
            }
        }

        When("id가 존재하지 않는다면"){
            val invalidId = -1L
            every { productRepository.findById(invalidId) } returns Optional.empty()
            every { orderRepository.findByBuyerIdOrSellerIdAndProductId(memberIdA, memberIdA, invalidId) } returns emptyList()
            then("Exception을 던진다."){
                val exception = shouldThrow<ProductException> { productService.findProductDetails(memberIdA, invalidId) }
                exception.errorCode shouldBe ErrorCode.PRODUCT_NOT_FOUND

            }
        }
    }


})