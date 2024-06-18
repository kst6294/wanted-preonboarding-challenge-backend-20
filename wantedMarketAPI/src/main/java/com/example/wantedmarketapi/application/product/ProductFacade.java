package com.example.wantedmarketapi.application.product;

import com.example.wantedmarketapi.domain.product.ProductCommand;
import com.example.wantedmarketapi.domain.product.ProductInfo;
import com.example.wantedmarketapi.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;

    public ProductInfo registerProduct(ProductCommand command) {
        ProductInfo productInfo = productService.registerProduct(command);
        return productInfo;
    }
}
