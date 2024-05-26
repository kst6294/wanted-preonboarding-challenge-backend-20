package com.wanted.preonboarding.module.product.repository;

import com.wanted.preonboarding.module.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
