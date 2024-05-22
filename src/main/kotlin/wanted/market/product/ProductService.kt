package wanted.market.product

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wanted.market.ErrorCode

@Service
@Transactional
class ProductService(@Autowired private val productRepository: ProductRepository) {

    fun findAllProducts(): List<ProductResponse> {
        return productRepository.findAll()
            .map { product-> ProductResponse.from(product)}

    }

    fun findProduct(id: Long): ProductResponse{
        val product = productRepository.findById(id).orElseThrow{
            throw ProductException(ErrorCode.PRODUCT_NOT_FOUND)
        }
        return ProductResponse.from(product)
    }


}