package com.chaewon.wanted.domain.orders.repository;

import com.chaewon.wanted.domain.orders.entity.OrderStatus;
import com.chaewon.wanted.domain.orders.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Query("SELECT o FROM Orders o WHERE o.buyer.email = :email AND o.orderStatus = :orderStatus")
    Page<Orders> findByBuyerEmailAndOrderStatus(@Param("email") String email, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE o.buyer.email = :email")
    Page<Orders> findByBuyerEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE o.seller.email = :email AND o.orderStatus = :orderStatus")
    Page<Orders> findBySellerEmailAndOrderStatus(@Param("email") String email, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

    @Query("SELECT o FROM Orders o JOIN FETCH o.seller JOIN FETCH o.product WHERE o.id = :orderId")
    Optional<Orders> findByIdWithSellerAndProduct(@Param("orderId") Long orderId);

}

