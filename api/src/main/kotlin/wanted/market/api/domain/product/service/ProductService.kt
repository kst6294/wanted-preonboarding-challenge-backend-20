package wanted.market.api.domain.product.service

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wanted.market.api.domain.member.service.MemberService
import wanted.market.api.domain.product.dto.RegisterProductRequest
import wanted.market.api.domain.product.dto.ProductInfo
import wanted.market.api.domain.product.entity.Product
import wanted.market.api.domain.product.repository.ProductRepository

@Service
@Transactional
class ProductService(
    private val productRepository: ProductRepository,
    private val memberService: MemberService
) { 
    fun findAllProducts(): List<ProductInfo> {
        var products = productRepository.findAll()
        return products.map { ProductInfo.from(it) }
    }

    fun findProduct(productId: Long): ProductInfo {
        var product = findProductById(productId)
        return ProductInfo.from(product)
    }

    private fun findProductById(productId: Long): Product {
        val product = productRepository.findById(productId)
            .orElseThrow { throw RuntimeException() }
        return product
    }

    fun registerProduct(registerProductRequest: RegisterProductRequest) {
      val member = memberService.findMember(registerProductRequest.sellerId)
      val newProduct = Product(
          name = registerProductRequest.name,
          price = registerProductRequest.price,
          seller = member
      )
      productRepository.save(newProduct)
    }
}
