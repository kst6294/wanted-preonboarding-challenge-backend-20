package org.example.wantedmarket.repository;

import org.example.wantedmarket.model.Order;
import org.example.wantedmarket.model.Product;
import org.example.wantedmarket.status.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllBySellerId(Long sellerId);
}
