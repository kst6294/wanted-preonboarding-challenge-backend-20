package wanted.market.order.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import wanted.market.common.exception.ErrorCode
import wanted.market.member.entity.Member
import wanted.market.member.service.MemberService
import wanted.market.order.OrderException
import wanted.market.order.entity.Order
import wanted.market.order.repository.OrderRepository
import wanted.market.product.entity.Product
import wanted.market.product.entity.ProductStatus
import wanted.market.product.service.ProductService
import java.util.*
import wanted.market.order.entity.OrderStatus

class OrderServiceTest: BehaviorSpec({
    val memberService = mockk<MemberService>(relaxed = true)
    val productService = mockk<ProductService>(relaxed = true)
    val orderRepository = mockk<OrderRepository>(relaxed = true)
    val orderService = OrderService(memberService, productService, orderRepository)

    val buyerId = 1L
    val sellerId = 2L
    val productId = 1L
    val orderId = 1L

    val buyer = Member("Buyer", "buyer@example.com", buyerId)
    val seller = Member("Seller", "seller@example.com", sellerId)
    val product = Product("Product", 1000, seller)
    val order = Order(buyer, seller, product, orderId)


    given("올바른 memberId/productId가 주어졌을 때"){
        When("주문을 하면"){
            every { memberService.findMember(buyerId) } returns buyer
            every { productService.findProductOnSale(productId) } returns product
            every { orderRepository.save(any<Order>()) } returns order

            then("새로운 order 객체를 생성하고 상품 상태를 바꾼다.") {
                orderService.order(buyerId, productId)
                verify(exactly = 1) { memberService.findMember(buyerId) }
                verify(exactly = 1) { productService.findProductOnSale(productId) }
                verify(exactly = 1) { orderRepository.save(any<Order>()) }
                product.productStatus shouldBe ProductStatus.RESERVED
            }
        }
    }

    given("판매자가 아닌 사람이"){
        every { orderRepository.findById(orderId) } returns Optional.of(order)
        When("판매 승인을 요청하면"){
            then("Exception을 던진다."){
                val exception = shouldThrow<OrderException> { orderService.approveOrder(buyerId, orderId) }
                exception.errorCode shouldBe ErrorCode.SELLER_APPROVAL_ONLY
                order.orderStatus shouldNotBe  OrderStatus.APPROVED
                order.product.productStatus shouldNotBe ProductStatus.SOLD
                verify(exactly = 1) { orderRepository.findById(orderId) }
            }
        }
    }

    given("판매자가"){
        every { orderRepository.findById(orderId) } returns Optional.of(order)
        When("판매 승인을 하면"){
            orderService.approveOrder(sellerId, orderId)
            then("상품 상태와 주문 상태를 변경한다."){
                order.orderStatus shouldBe OrderStatus.APPROVED
                order.completeDate shouldNotBe null
                order.product.productStatus shouldBe ProductStatus.SOLD

//                verify(exactly = 1) { orderRepository.findById(orderId) }
            }
        }
    }
})