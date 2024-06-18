package com.example.wantedmarketapi.infrastructure.product;

import com.example.wantedmarketapi.domain.product.Product;
import com.example.wantedmarketapi.domain.product.ProductStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductStoreImpl implements ProductStore {

    private final ProductRepository productRepository;
    @Override
    public Product store(Product product) {
        return productRepository.save(product);
    }
}
