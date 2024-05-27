package com.chaewon.wanted.domain.product.repository;

import com.chaewon.wanted.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
