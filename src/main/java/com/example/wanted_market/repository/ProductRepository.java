package com.example.wanted_market.repository;

import com.example.wanted_market.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p ORDER BY p.createDate DESC")
    List<Product> findAllProducts();

    Optional<Product> findProductById(Long productId);
}
