package wanted.market.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wanted.market.product.entity.Product

@Repository
interface ProductRepository : JpaRepository<Product, Long>{


}