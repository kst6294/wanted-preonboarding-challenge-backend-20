package wanted.market

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.EnableTransactionManagement
import wanted.market.member.Member
import wanted.market.member.MemberRepository
import wanted.market.product.Product
import wanted.market.product.ProductRepository

@Component
class Initializer(@Autowired private val productRepository: ProductRepository,
                  @Autowired private val memberRepository: MemberRepository) {

    @PostConstruct
    @Transactional
    fun saveProduct() {
        val member = Member("노을", "hello@gmail.com", 1L)
        memberRepository.save(member)

        productRepository.save(Product("상품1", 100, member))
        productRepository.save(Product("상품2", 200, member))

    }
}