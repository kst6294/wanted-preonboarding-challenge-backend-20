package wanted.market.api.product.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.member.domain.entity.Member
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

    fun registerProduct(request: CommandRegisterProduct, sellerId: Long) : CommandRegisterProductResult {
        val seller = findMemberById(sellerId)

        val products = Product.register(request, seller)

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

        val product = findProductById(request.productId)

        val buyer = findMemberById(buyerId)

        checkPurchasable(product, buyerId)

        product.purchase(request.quantity)

        val order = createOrder(buyer, product)

        val orderItem = createOrderItem(product, request, order)

        orderItemRepository.save(orderItem)

        return CommandPurchaseProductResult.from(order, product, request.quantity)
    }

    private fun createOrderItem(
        product: Product,
        request: CommandPurchaseProduct,
        order: Order
    ) = OrderItem.create(product, request.quantity, order)

    private fun createOrder(
        buyer: Member,
        product: Product
    ) = Order.create(buyer, product.seller)

    private fun checkPurchasable(product: Product, buyerId: Long) {
        productRepository.isPurchasable(product.id!!, buyerId).let {
            if (it > 0) {
                throw IllegalArgumentException("이미 구매한 상품입니다.")
            }
        }
    }

    private fun findMemberById(buyerId: Long) : Member {
        return memberRepository.findByIdOrNull(buyerId)
            ?: throw IllegalArgumentException("회원이 존재하지 않습니다.")
    }

    private fun findProductById(productId: Long) : Product {
        return productRepository.findByIdOrNull(productId)
            ?: throw IllegalArgumentException("상품이 존재하지 않습니다.")
    }
}