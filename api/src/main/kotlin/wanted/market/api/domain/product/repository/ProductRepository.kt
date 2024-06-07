package wanted.market.api.domain.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wanted.market.api.domain.product.entity.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long>{
}
