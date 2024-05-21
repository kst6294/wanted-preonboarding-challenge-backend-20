package wanted.market.api.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import wanted.market.api.product.domain.entity.Product

interface ProductRepository : JpaRepository<Product, Long> {
}