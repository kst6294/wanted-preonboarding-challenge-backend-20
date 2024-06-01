package com.wanted.preonboarding.domain.product.repository;

import com.wanted.preonboarding.domain.product.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"user"})
    Optional<Product> findById(Long id);
}
