package com.sunyesle.wanted_market.repository;

import com.sunyesle.wanted_market.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
