package com.wanted.market.product.domain;

import com.wanted.market.common.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    default Product findByIdOrThrow(Long id) throws NotFoundException {
        return findById(id).orElseThrow(() -> new NotFoundException("no such product"));
    }
}
