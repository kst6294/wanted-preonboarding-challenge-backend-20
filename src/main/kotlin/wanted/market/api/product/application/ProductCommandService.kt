package wanted.market.api.product.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.repository.MemberRepository
import wanted.market.api.order.domain.entity.Order
import wanted.market.api.order.domain.entity.OrderItem
import wanted.market.api.order.repository.OrderItemRepository
import wanted.market.api.product.domain.dto.`in`.CommandPurchaseProduct
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct
import wanted.market.api.product.domain.dto.out.CommandPurchaseProductResult
import wanted.market.api.product.domain.dto.out.CommandRegisterProductResult
import wanted.market.api.product.domain.entity.Product
import wanted.market.api.product.repository.ProductRepository

@Service
@Transactional
class ProductCommandService(
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
    private val orderItemRepository: OrderItemRepository
) {

    fun registerProduct(request: CommandRegisterProduct, memberId: Long) : CommandRegisterProductResult {

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw IllegalArgumentException("회원이 존재하지 않습니다.")

        val products = Product.register(request, member)

        productRepository.save(products)

        return CommandRegisterProductResult(
            sellerId = products.seller.id!!,
            id = products.id!!,
            name = products.name,
            price = products.price,
            quantity = products.quantity,
            status = products.status.toString()
        )
    }

    fun purchaseProduct(request: CommandPurchaseProduct, buyerId: Long): CommandPurchaseProductResult {

        val product = productRepository.findByIdOrNull(request.productId)
            ?: throw IllegalArgumentException("상품이 존재하지 않습니다.")

        val buyer = memberRepository.findByIdOrNull(buyerId)
            ?: throw IllegalArgumentException("회원이 존재하지 않습니다.")

        productRepository.isPurchasable(product.id!!, buyerId).let {
            if (it > 0) {
                throw IllegalArgumentException("이미 구매한 상품입니다.")
            }
        }

        product.purchase(request.quantity)

        val order = Order.create(buyer, product.seller)

        val orderItem = OrderItem.create(product, request.quantity, order)

        orderItemRepository.save(orderItem)

        return CommandPurchaseProductResult.from(product, request.quantity)
    }
}