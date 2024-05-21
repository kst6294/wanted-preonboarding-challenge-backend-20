package wanted.market.api.product.application

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wanted.market.api.product.domain.dto.out.RetrieveProductResult
import wanted.market.api.product.repository.ProductRepository

@Service
@Transactional(readOnly = true)
class ProductService(
    private val productRepository: ProductRepository
) {
    fun findAllProducts(): List<RetrieveProductResult> {
        val products = productRepository.findAll()
        return products.map { RetrieveProductResult.from(it) }
    }

    fun findOneProduct(productId: Long): RetrieveProductResult {
        val product = productRepository.findByIdOrNull(productId)
            ?: throw IllegalArgumentException("제품을 찾을 수 없습니다.")
        return RetrieveProductResult.from(product)
    }
}