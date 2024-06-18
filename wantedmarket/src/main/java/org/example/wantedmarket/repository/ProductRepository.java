package org.example.wantedmarket.repository;

import jakarta.persistence.LockModeType;
import org.example.wantedmarket.model.Order;
import org.example.wantedmarket.model.Product;
import org.example.wantedmarket.status.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllBySellerId(Long sellerId);
    @Lock(value= LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Product findByWithPessimisticLock(final Long id);
}
