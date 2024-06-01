package org.example.preonboarding.order.repository;

import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.order.model.domain.Order;
import org.example.preonboarding.order.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndOrderStatus(Long id, OrderStatus orderStatus);

    @Query(value = "SELECT o.* FROM orders o WHERE o.buyer_id = :memberId OR o.seller_id = :memberId", nativeQuery = true)
    List<Order> findAllByMemberId(@Param("memberId") Long memberId);

    List<Order> findAllByBuyer(Member buyer);

    List<Order> findAllBySeller(Member seller);

    @Query(value = "SELECT COUNT(*) FROM orders WHERE product_id = :productId AND order_status != 'COMPLETED'", nativeQuery = true)
    long countNonCompletedOrdersByProductId(@Param("productId") Long productId);
}
