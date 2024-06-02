package com.wanted.preonboarding.domain.purchase.repository;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.product.entity.ProductState;
import com.wanted.preonboarding.domain.purchase.entity.Purchase;
import com.wanted.preonboarding.domain.user.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    boolean existsByUserAndProduct(User user, Product product);
    boolean existsByProductAndProductState(Product product, ProductState state);

    @EntityGraph(attributePaths = {"user", "product"})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Purchase p where p.id = :id")
    Optional<Purchase> findByIdWithLock(Long id);

}
