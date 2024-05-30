package wanted.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.entity.Product;
import wanted.market.entity.User;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller(User seller);
}
