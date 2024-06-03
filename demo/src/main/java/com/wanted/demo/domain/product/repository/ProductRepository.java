package com.wanted.demo.domain.product.repository;


import com.wanted.demo.domain.product.dto.response.ProductResponseDTO;
import com.wanted.demo.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.wanted.demo.domain.product.dto.response.ProductResponseDTO(p.id, p.user.id, p.name, p.price, p.quantity, p.state)" +
            "FROM Product p")
    List<ProductResponseDTO> findAllProducts();



}
