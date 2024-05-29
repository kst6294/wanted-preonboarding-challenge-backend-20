package wanted.market.api.domain.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.api.domain.orders.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
