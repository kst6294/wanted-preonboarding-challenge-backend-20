package wanted.market.product.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wanted.market.product.entity.Product
import wanted.market.product.entity.ProductStatus
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long>{

    fun findByIdAndProductStatus(id: Long, productStatus: ProductStatus): Optional<Product>
}