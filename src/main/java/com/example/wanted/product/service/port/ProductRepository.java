package com.example.wanted.product.service.port;

import com.example.wanted.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> find();
}
