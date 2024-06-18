package com.example.wantedmarketapi.infrastructure.product;

import com.example.wantedmarketapi.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
