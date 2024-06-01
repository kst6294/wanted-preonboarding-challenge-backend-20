package com.wanted.preonboarding.domain.repository;

import com.wanted.preonboarding.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
