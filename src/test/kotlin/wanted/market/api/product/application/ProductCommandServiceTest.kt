package wanted.market.api.product.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.application.MemberAuthService
import wanted.market.api.member.domain.dto.`in`.CommandRegisterMember
import wanted.market.api.member.domain.dto.out.CommandRegisterMemberResult
import wanted.market.api.product.domain.dto.`in`.CommandPurchaseProduct
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct
import wanted.market.api.product.domain.dto.out.CommandRegisterProductResult
import wanted.market.api.product.domain.entity.ProductStatus

@SpringBootTest
@Transactional
class ProductCommandServiceTest(
    private val productCommandService: ProductCommandService,
    private val memberAuthService: MemberAuthService
) : StringSpec({

    fun registerMember(): CommandRegisterMemberResult {
        val request = CommandRegisterMember(
            userId = "seonwoo_jung",
            password = "12345678a"
        )

        // when
        return memberAuthService.registerMember(request)
    }

    fun registerProduct() : CommandRegisterProductResult {
        val memberInfo = registerMember()

        val productRequest = CommandRegisterProduct(
            name = "상품명",
            price = 1_000_000,
            quantity = 2,
            status = ProductStatus.SALE
        )
        return productCommandService.registerProduct(productRequest, memberInfo.id)
    }

    "상품을 등록한다" {
        // given
        val memberInfo = registerMember()

        val productRequest = CommandRegisterProduct(
            name = "상품명",
            price = 1_000_000,
            quantity = 2,
            status = ProductStatus.SALE
        )

        // when
        val response = productCommandService.registerProduct(productRequest, memberInfo.id)

        // then
        response.name shouldBe productRequest.name
        response.price shouldBe productRequest.price
        response.quantity shouldBe productRequest.quantity
        response.status shouldBe productRequest.status.toString()
    }

    "상품을 구매한다" {
        // given
        val product = registerProduct()

        val purchaseRequest = CommandPurchaseProduct(
            productId = product.id,
            quantity = 2
        )

        // when
        val purchaseResponse = productCommandService.purchaseProduct(purchaseRequest, product.sellerId)

        // then
        purchaseResponse.id shouldBe purchaseRequest.productId
        purchaseResponse.quantity shouldBe purchaseRequest.quantity
    }

    "등록된 상품보다 많은 수량을 구매하려고 할 때 예외가 발생한다" {
        // given
        val product = registerProduct()

        val purchaseRequest = CommandPurchaseProduct(
            productId = product.id,
            quantity = 3
        )

        // when
        val exception = shouldThrow<IllegalArgumentException> {
            productCommandService.purchaseProduct(purchaseRequest, product.sellerId)
        }

        // then
        exception.message shouldBe "상품의 재고가 부족합니다."
    }
})
