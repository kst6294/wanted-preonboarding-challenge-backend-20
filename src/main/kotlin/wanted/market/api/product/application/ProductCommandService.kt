package wanted.market.api.product.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.product.domain.dto.`in`.CommandPurchaseProduct
import wanted.market.api.product.domain.dto.`in`.CommandRegisterProduct
import wanted.market.api.product.domain.dto.out.CommandPurchaseProductResult
import wanted.market.api.product.domain.dto.out.CommandRegisterProductResult
import wanted.market.api.product.domain.entity.Product
import wanted.market.api.product.repository.ProductRepository

@Service
@Transactional
class ProductCommandService(
    private val productRepository: ProductRepository
) {

    fun registerProduct(request: CommandRegisterProduct) : CommandRegisterProductResult {
        val products = Product.register(request)
        productRepository.save(products)
        return CommandRegisterProductResult(
            id = products.id!!,
            name = products.name,
            price = products.price,
            quantity = products.quantity,
            status = products.status.toString()
        )
    }

    fun purchaseProduct(request: CommandPurchaseProduct): CommandPurchaseProductResult {
        val product = productRepository.findByIdOrNull(request.productId)
            ?: throw IllegalArgumentException("상품이 존재하지 않습니다.")
        product.purchase(request.quantity)
        return CommandPurchaseProductResult.from(product.id!!, product.quantity)
    }
}