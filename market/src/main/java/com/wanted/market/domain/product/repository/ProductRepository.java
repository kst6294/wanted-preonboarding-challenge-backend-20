package com.wanted.market.domain.product.repository;

import com.wanted.market.domain.product.dto.ProductDto;
import com.wanted.market.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT new com.wanted.market.domain.product.dto.ProductDto(p.productNo, p.name, p.price, p.status) FROM Product p ")
    Page<ProductDto> findAllProducts(Pageable pageable);

    Optional<Product> findProductByProductNo(long productNo);
}
