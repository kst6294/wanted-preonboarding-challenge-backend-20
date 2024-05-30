package com.example.wantedmarketapi.repository;

import com.example.wantedmarketapi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndSellerId(Long productId, Long memberId);

}
