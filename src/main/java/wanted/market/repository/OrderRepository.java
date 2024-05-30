package wanted.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
