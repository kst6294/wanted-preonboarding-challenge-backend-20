package org.example.wantedmarket.repository;

import org.example.wantedmarket.domain.Product;

import java.util.List;

public interface ProductRepository {

    Product getById(Long id);

    List<Product> findAll();

}
