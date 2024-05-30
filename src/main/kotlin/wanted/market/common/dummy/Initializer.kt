package wanted.market.common.dummy

import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import wanted.market.member.entity.Member
import wanted.market.member.repository.MemberRepository
import wanted.market.product.repository.ProductRepository

@Component
class Initializer(@Autowired private val productRepository: ProductRepository,
                  @Autowired private val memberRepository: MemberRepository,
                  @Autowired private val entityManager: EntityManager) {

    @PostConstruct
    @Transactional
    fun saveProduct() {

        val member = Member("노을", "hello@gmail.com")
//        entityManager.persist(member)
//        memberRepository.save(member)
//
//        productRepository.save(Product("상품1", 100, member))
//        productRepository.save(Product("상품2", 200, member))
//
//        entityManager.flush()

    }
}