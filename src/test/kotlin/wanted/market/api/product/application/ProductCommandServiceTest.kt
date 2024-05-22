package wanted.market.api.product.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.domain.entity.Member
import wanted.market.api.member.repository.MemberRepository
import wanted.market.api.product.domain.dto.`in`.CommandPurchaseProduct
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct
import wanted.market.api.product.domain.entity.Product
import wanted.market.api.product.repository.ProductRepository

@SpringBootTest
@Transactional
class ProductCommandServiceTest(
    private val productCommandService: ProductCommandService,
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository
) : StringSpec({

    lateinit var member: Member
    lateinit var product: Product

    beforeTest {
        member = memberRepository.findByIdOrNull(2L) ?: throw IllegalArgumentException("회원을 찾을 수 없습니다.")
        product = productRepository.findByIdOrNull(2L) ?: throw IllegalArgumentException("제품을 찾을 수 없습니다.")
    }

    "상품을 등록한다" {
        val productRequest = CommandRegisterProduct(
            name = "상품명",
            price = 1_000_000,
            quantity = 2
        )

        // when
        val response = productCommandService.registerProduct(productRequest, member.id!!)

        // then
        response.name shouldBe productRequest.name
        response.price shouldBe productRequest.price
        response.quantity shouldBe productRequest.quantity
    }

    "상품을 구매한다" {
        // given
        val purchaseRequest = CommandPurchaseProduct(
            productId = product.id!!,
            quantity = 1
        )

        // when
        val purchaseResponse = productCommandService.purchaseProduct(purchaseRequest, member.id!!)

        // then
        purchaseResponse.productId shouldBe purchaseRequest.productId
        purchaseResponse.quantity shouldBe purchaseRequest.quantity
    }

    "등록된 상품보다 많은 수량을 구매하려고 할 때 예외가 발생한다" {
        // given
        val purchaseRequest = CommandPurchaseProduct(
            productId = product.id!!,
            quantity = 3
        )

        // when
        val exception = shouldThrow<IllegalArgumentException> {
            productCommandService.purchaseProduct(purchaseRequest, member.id!!)
        }

        // then
        exception.message shouldBe "상품의 재고가 부족합니다."
    }
})
