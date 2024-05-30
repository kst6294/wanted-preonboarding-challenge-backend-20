package wanted.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.entity.Order;
import wanted.market.entity.Product;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByProduct(Product product);
}
