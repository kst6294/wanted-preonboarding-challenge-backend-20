package com.wanted.wantedpreonboardingchallengebackend20.domain.product.repository;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>,ProductRepositoryCustom {
}
