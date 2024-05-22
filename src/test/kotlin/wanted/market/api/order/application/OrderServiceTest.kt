package wanted.market.api.order.application

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.application.MemberAuthService
import wanted.market.api.member.domain.dto.`in`.CommandRegisterMember
import wanted.market.api.member.domain.dto.out.CommandRegisterMemberResult
import wanted.market.api.order.domain.entity.OrderStatus.SALEAPPROVAL
import wanted.market.api.product.application.ProductCommandService
import wanted.market.api.product.domain.dto.`in`.CommandPurchaseProduct
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct
import wanted.market.api.product.domain.dto.out.CommandRegisterProductResult
import wanted.market.api.product.repository.ProductRepository

@SpringBootTest
@Transactional
class OrderServiceTest(
    private val orderService: OrderService,
    private val memberAuthService: MemberAuthService,
    private val productCommandService: ProductCommandService,
    private val em: EntityManager,
    private val productRepository: ProductRepository
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
            quantity = 2
        )
        return productCommandService.registerProduct(productRequest, memberInfo.id)
    }

    "판매자가 제품 판매 승인을 한다" {
        val product = registerProduct()

        val purchaseRequest = CommandPurchaseProduct(
            productId = product.id,
            quantity = 2
        )

        // when
        val purchaseResponse = productCommandService.purchaseProduct(purchaseRequest, product.sellerId)

        val order = orderService.approveOrder(purchaseResponse.id, product.sellerId)

        order.orderStatus shouldBe SALEAPPROVAL
        em.flush()
    }

    "구매자가 구매 확정을 한다" {
        val product = registerProduct()

        val purchaseRequest = CommandPurchaseProduct(
            productId = product.id,
            quantity = 2
        )

        // when
        val purchaseResponse = productCommandService.purchaseProduct(purchaseRequest, product.sellerId)

        val order = orderService.approveOrder(purchaseResponse.id, product.sellerId)

        em.flush()

        orderService.confirmOrder(order.orderId, product.sellerId)
        em.flush()
    }
})
