package com.wanted.challenge.purchase.repository;

import com.wanted.challenge.purchase.entity.Purchase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>, PurchaseRepositoryCustom {

    List<Purchase> findByBuyerId(Long buyerId);

    List<Purchase> findByProductId(Long productId);
}
