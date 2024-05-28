package com.wanted.preonboarding.backend20.domain.product.repository;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.domain.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findBySellerAndStatusEquals(Member seller, ProductStatus status, Pageable pageable);
}
