package com.wanted.challenge.product.repository;

import com.wanted.challenge.product.entity.Purchase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByBuyerId(Long buyerId);
}
