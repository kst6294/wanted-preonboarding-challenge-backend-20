package com.wanted.preonboarding.domain.product.repository;

import com.wanted.preonboarding.domain.product.entity.Product;
import com.wanted.preonboarding.domain.product.entity.ProductState;
import com.wanted.preonboarding.domain.user.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"user"})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdWithLock(Long id);

    @EntityGraph(attributePaths = {"user"})
    Optional<Product> findById(Long id);

    @EntityGraph(attributePaths = {"user"})
    List<Product> findAllByUserAndState(User user, ProductState productState);
}
