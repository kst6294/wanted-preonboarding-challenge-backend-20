package com.example.wantedmarketapi.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductStore productStore;

    @Override
    public ProductInfo registerProduct(ProductCommand command) {
        Product initProduct = command.toEntity();
        Product product = productStore.store(initProduct);
        return new ProductInfo(product);
    }
}
