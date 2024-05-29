package com.example.wanted_market.repository;

import com.example.wanted_market.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p")
    List<Product> findAllProductDetails();
}
