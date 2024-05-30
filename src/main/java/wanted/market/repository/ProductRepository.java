package wanted.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
