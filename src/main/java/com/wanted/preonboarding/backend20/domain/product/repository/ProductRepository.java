package com.wanted.preonboarding.backend20.domain.product.repository;

import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
