package wanted.market.product.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wanted.market.common.ErrorCode
import wanted.market.member.service.MemberService
import wanted.market.product.exception.ProductException
import wanted.market.product.dto.SaveProductRequest
import wanted.market.product.dto.ProductResponse
import wanted.market.product.entity.Product
import wanted.market.product.entity.ProductStatus
import wanted.market.product.repository.ProductRepository

@Service
@Transactional
class ProductService(@Autowired private val productRepository: ProductRepository,
                     @Autowired private val memberService: MemberService
) {
    fun findAllProducts(): List<ProductResponse> {
        return productRepository.findAll()
            .map { product-> ProductResponse.from(product)}
    }

    fun findProduct(id: Long): ProductResponse {
        val product = findProductById(id)
        return ProductResponse.from(product)
    }

    private fun findProductById(id: Long): Product {
        val product = productRepository.findById(id).orElseThrow {
            throw ProductException(ErrorCode.PRODUCT_NOT_FOUND)
        }
        return product
    }

    fun findProductOnSale(id: Long): Product {
        val product = findProductById(id)
        if (product.productStatus != ProductStatus.SALE) {
            throw ProductException(ErrorCode.PRODUCT_NOT_FOR_SALE)
        }
        return product
    }

    fun saveProduct(saveProductRequest: SaveProductRequest) {
        val member = memberService.findMember(saveProductRequest.memberId)
        val newProduct = Product(saveProductRequest.name, saveProductRequest.price, member)
        productRepository.save(newProduct)
    }
}