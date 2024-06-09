package com.sunyesle.wanted_market.service;

import com.sunyesle.wanted_market.dto.ProductDetailResponse;
import com.sunyesle.wanted_market.dto.ProductRequest;
import com.sunyesle.wanted_market.dto.ProductResponse;
import com.sunyesle.wanted_market.entity.Product;
import com.sunyesle.wanted_market.exception.ErrorCodeException;
import com.sunyesle.wanted_market.exception.ProductErrorCode;
import com.sunyesle.wanted_market.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse save(ProductRequest request, Long memberId) {
        Product product = new Product(memberId, request.getName(), request.getPrice(), request.getQuantity());
        productRepository.save(product);
        return new ProductResponse(product.getId());
    }

    public ProductDetailResponse getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ErrorCodeException(ProductErrorCode.PRODUCT_NOT_FOUND));
        return ProductDetailResponse.of(product);
    }

    public List<ProductDetailResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDetailResponse::of).toList();
    }
}
