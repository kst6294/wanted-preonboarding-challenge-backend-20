package com.wanted.preonboarding.domain.product.repository;

import com.wanted.preonboarding.domain.product.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Product> findAll();
}
