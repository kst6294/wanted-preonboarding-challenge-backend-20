package com.sunyesle.wanted_market.service;

import com.sunyesle.wanted_market.dto.ProductDetailResponse;
import com.sunyesle.wanted_market.dto.ProductRequest;
import com.sunyesle.wanted_market.dto.ProductResponse;
import com.sunyesle.wanted_market.entity.Product;
import com.sunyesle.wanted_market.enums.ProductStatus;
import com.sunyesle.wanted_market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse save(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .status(ProductStatus.AVAILABLE).build();
        productRepository.save(product);
        return new ProductResponse(product.getId());
    }

    public ProductDetailResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);
        return ProductDetailResponse.of(product);
    }
}
