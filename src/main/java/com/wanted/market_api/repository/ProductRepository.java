package com.wanted.market_api.repository;

import com.wanted.market_api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p LEFT JOIN FETCH p.member m WHERE p.id = :productId")
    Optional<Product> findWithMemberById(long productId);
}
