package com.wanted.demo.domain.product.repository;


import com.wanted.demo.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
