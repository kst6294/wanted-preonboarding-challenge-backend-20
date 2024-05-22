package wanted.market.api.order.application

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.domain.entity.Member
import wanted.market.api.member.repository.MemberRepository
import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderStatus.SALEAPPROVAL
import wanted.market.api.order.repository.OrderRepository
import wanted.market.api.product.domain.entity.Product
import wanted.market.api.product.repository.ProductRepository

@SpringBootTest
@Transactional
class OrderServiceTest(
    private val orderService: OrderService,
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
) : StringSpec({

    lateinit var member: Member
    lateinit var product: Product
    lateinit var order: Order

    beforeTest {
        member = memberRepository.findByIdOrNull(1L) ?: throw IllegalArgumentException("회원을 찾을 수 없습니다.")
        product = productRepository.findByIdOrNull(1L) ?: throw IllegalArgumentException("제품을 찾을 수 없습니다.")
        order = orderRepository.findByIdOrNull(1L) ?: throw IllegalArgumentException("주문을 찾을 수 없습니다.")
    }

    "판매자가 제품 판매 승인을 한다" {
        val order = orderService.approveOrder(order.id!!, member.id!!)
        order.orderStatus shouldBe SALEAPPROVAL
    }

    "구매자가 구매 확정을 한다" {
        val approveOrder = orderService.approveOrder(order.id!!, member.id!!)
        val responseOrder = orderService.confirmOrder(approveOrder.orderId, member.id!!)

        responseOrder shouldBe order.id!!
    }
})
