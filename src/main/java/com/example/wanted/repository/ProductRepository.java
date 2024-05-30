package com.example.wanted.repository;

import com.example.wanted.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findByDescriptionContaining(String description, Pageable pageable);

}
