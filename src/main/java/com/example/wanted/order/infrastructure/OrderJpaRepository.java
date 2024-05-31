package com.example.wanted.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT o FROM OrderEntity o WHERE o.seller.id = :userId OR o.buyer.id = :userId")
    List<OrderEntity> findOrdersByUserId(@Param("userId") Long userId);
}
