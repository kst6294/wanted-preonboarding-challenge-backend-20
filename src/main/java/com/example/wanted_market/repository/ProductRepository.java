package com.example.wanted_market.repository;

import com.example.wanted_market.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
