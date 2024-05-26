package wanted.market

import jakarta.annotation.PostConstruct
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import wanted.market.member.Member
import wanted.market.product.Product
import wanted.market.product.ProductRepository

@Component
@Transactional
class Initializer(@Autowired private val productRepository: ProductRepository) {

    @PostConstruct
    fun saveProduct() {
        val member = Member(1L, "노을", "hello@gmail.com")
        productRepository.save(Product("상품1", 1, member))
        productRepository.save(Product("상품2", 1, member))
    }
}