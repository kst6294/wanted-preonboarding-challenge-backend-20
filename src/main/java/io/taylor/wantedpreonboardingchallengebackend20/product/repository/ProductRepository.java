package io.taylor.wantedpreonboardingchallengebackend20.product.repository;

import io.taylor.wantedpreonboardingchallengebackend20.product.entity.Product;
import io.taylor.wantedpreonboardingchallengebackend20.product.model.request.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(ProductRequest product);
    List<Product> findAll();
    Product findById(long id);
}
