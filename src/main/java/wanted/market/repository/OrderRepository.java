package wanted.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wanted.market.entity.Order;
import wanted.market.entity.OrderStatus;
import wanted.market.entity.Product;
import wanted.market.entity.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByProduct(Product product);

    List<Order> findByBuyer(User buyer);

    @Query("select count(*) from Order where product = :product and orderStatus = :status")
    long countByProductAndOrderStatus(@Param("product") Product product, @Param("status") OrderStatus status);
}
