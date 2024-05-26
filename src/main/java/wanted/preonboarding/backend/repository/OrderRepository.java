package wanted.preonboarding.backend.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wanted.preonboarding.backend.domain.entity.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Query("select o, i.name from Orders o join Item i on o.item.id = i.id where o.member.id = :memberId and o.item.id = :itemId")
    Optional<Orders> findOrderHistory(Long memberId, Long itemId);

    @Query("select o, i.name from Orders o join Item i on o.item.id = i.id where o.member.id = :memberId and o.status = :status")
    List<Orders> findOrdersWithStatus(Long memberId, Orders.OrderStatus status);

    @Query("select o, i.name from Orders o join Item i on o.item.id = i.id where i.member.id = :memberId and o.status = :status")
    List<Orders> findRequestedOrders(Long memberId, Orders.OrderStatus status);

    @Query("select o, i from Orders o join Item i on o.item.id = i.id")
    Optional<Orders> findByOrdersId(Long ordersId);
}
