package com.wanted.market.order.domain;

import com.wanted.market.common.exception.NotFoundException;
import com.wanted.market.order.domain.vo.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByProductIdAndBuyerId(Long productId, Long buyerId);

    boolean existsByProductIdAndStatus(Long productId, Status status);

    @Query("select p.sellerId from Product p where p.id = :id")
    Long findSellerIdByProductId(Long id);

    default Order findByIdOrThrow(Long id) throws NotFoundException {
        return findById(id).orElseThrow(() -> new NotFoundException("no such order"));
    }
}
