package com.example.wanted.product.infrastructure;

import com.example.wanted.product.domain.Product;
import com.example.wanted.product.service.port.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;
    @Override
    public Product save(Product product) {
        return productJpaRepository.save(ProductEntity.fromModel(product)).toModel();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id).map(ProductEntity::toModel);
    }

    @Override
    public List<Product> find() {
        return productJpaRepository
                .findAll()
                .stream()
                .map(ProductEntity::toModel)
                .collect(Collectors.toList());
    }
}
