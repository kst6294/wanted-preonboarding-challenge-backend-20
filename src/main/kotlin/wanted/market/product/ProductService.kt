package wanted.market.product

import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import wanted.market.ErrorCode
import wanted.market.member.MemberService

@Service
@Transactional
class ProductService(@Autowired private val productRepository: ProductRepository,
                     @Autowired private val memberService: MemberService) {

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

    fun saveProduct(saveProductRequest: SaveProductRequest) {
        val member = memberService.findMember(saveProductRequest.memberId)
        val newProduct = Product(saveProductRequest.name, saveProductRequest.price, member)
        productRepository.save(newProduct)
    }
}