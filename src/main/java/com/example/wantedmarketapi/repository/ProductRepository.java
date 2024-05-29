package com.example.wantedmarketapi.repository;

import com.example.wantedmarketapi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
