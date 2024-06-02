package com.example.wanted.order.infrastructure;

import com.example.wanted.order.domain.OrderStatus;
import com.example.wanted.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    @Query("SELECT o FROM OrderEntity o WHERE o.seller.id = :userId OR o.buyer.id = :userId")
    List<OrderEntity> findByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM OrderEntity o WHERE (o.seller.id = :userId OR o.buyer.id = :userId) AND (o.product.id = :productId)")
    List<OrderEntity> findByUserIdOrProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    List<OrderEntity> findByProductAndStatusIn(Product product, List<OrderStatus> status);

}
