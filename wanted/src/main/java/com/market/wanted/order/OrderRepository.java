package com.market.wanted.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.seller s join fetch o.buyer b where itemId = :itemId and b.id = :memberId")
    List<Order> findBuyHistory(@Param("memberId")Long memberId, @Param("itemId")Long itemId);
    @Query("select o from Order o join fetch o.seller s join fetch o.buyer b where itemId = :itemId and s.id = :memberId")
    List<Order> findSellHistory(@Param("memberId")Long memberId, @Param("itemId")Long itemId);
    @Query("select o from Order o join fetch o.buyer b where status = :status and b.id = :memberId")
    List<Order> findBuyItem(@Param("memberId") Long memberId, @Param("status") OrderStatus status);
    @Query("select o from Order o join fetch o.buyer b join fetch o.seller s where status != :status and b.id = :memberId")
    List<Order> findReservationBuyItem(@Param("memberId")Long memberId, @Param("status") OrderStatus status);
    @Query("select o from Order o join fetch o.buyer b join fetch o.seller s where status != :status and s.id = :memberId")
    List<Order> findReservationSellItem(@Param("memberId")Long memberId, @Param("status") OrderStatus status);

}
