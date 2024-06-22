package org.example.wantedmarket.repository.jpa;

import jakarta.persistence.LockModeType;
import org.example.wantedmarket.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByOwnerId(Long ownerId);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Product findByIdWithPessimisticLock(final Long id);

}
