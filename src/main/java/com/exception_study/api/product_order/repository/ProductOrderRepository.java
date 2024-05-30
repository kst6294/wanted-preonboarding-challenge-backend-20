package com.exception_study.api.product_order.repository;

import com.exception_study.api.product_order.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Integer> {


    @Query("select o from ProductOrder o where o.buyerStatus = '완료' and o.buyer.userId = :userId")
    List<ProductOrder> findAllByBuyer(@Param("userId") String userId);

    @Query("select o from ProductOrder o where (o.sellerStatus = '예약중' and o.seller.userId = :userId ) or (o.buyerStatus = '예약중' and o.buyer.userId = :userId)")
    List<ProductOrder> findReservedProducts(@Param("userId") String userId);

    @Query("select o from ProductOrder o where (o.sellerStatus = '완료' and o.seller.userId = :seller) and (o.buyerStatus = '완료' and o.buyer.userId = :buyer )")
    List<ProductOrder> findTransaction_History(@Param("seller") String seller, @Param("buyer") String buyer);

}
