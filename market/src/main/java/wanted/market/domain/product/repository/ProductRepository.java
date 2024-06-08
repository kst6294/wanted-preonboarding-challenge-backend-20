package wanted.market.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.market.domain.product.repository.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
