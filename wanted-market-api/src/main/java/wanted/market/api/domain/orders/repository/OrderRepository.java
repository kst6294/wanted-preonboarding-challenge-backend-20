package wanted.market.api.domain.orders.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import wanted.market.api.domain.orders.entity.Order;
import wanted.market.api.domain.orders.enums.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"product"})
    List<Order> findAllByUserId(Long userId);
    @EntityGraph(attributePaths = {"product"})
    List<Order> findAllByProductUserId(Long userId);
    @EntityGraph(attributePaths = {"product"})
    List<Order> findAllByUserIdAndProductUserIdAndStatus(Long userId, Long targetUserId, OrderStatus status);

}
