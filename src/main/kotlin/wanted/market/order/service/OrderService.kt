package wanted.market.order.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wanted.market.common.exception.ErrorCode
import wanted.market.member.service.MemberService
import wanted.market.order.OrderException
import wanted.market.order.entity.Order
import wanted.market.order.entity.OrderStatus
import wanted.market.order.repository.OrderRepository
import wanted.market.product.entity.ProductStatus
import wanted.market.product.service.ProductService

@Service
class OrderService(@Autowired private val memberService: MemberService,
                   @Autowired private val productService: ProductService,
                   @Autowired private val orderRepository: OrderRepository) {
    @Transactional
    fun order(memberId: Long, productId: Long) {
        //TODO : Order 완료 시점 저장

        val buyer = memberService.findMember(memberId)
        val product = productService.findProductOnSale(productId)
        product.productStatus = ProductStatus.RESERVED
        orderRepository.save(Order(buyer, product))
    }

    @Transactional
    fun approveOrder(memberId: Long, orderId: Long) {

        //TODO : 페치 조인으로 쿼리 개수 줄이기
        val order = orderRepository.findById(orderId)
            .orElseThrow { OrderException(ErrorCode.ORDER_NOT_FOUND)}

        //TODO : 셀러와 주어진 멤버의 id가 같아야 함.
        order.orderStatus = OrderStatus.APPROVED
        order.product.productStatus = ProductStatus.SOLD
    }

    @Transactional
    fun findOrder(memberId: Long, productId: Long) {
    }
}