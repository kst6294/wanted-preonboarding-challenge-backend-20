package com.sunyesle.wanted_market.product;

import com.sunyesle.wanted_market.product.dto.ProductDetailResponse;
import com.sunyesle.wanted_market.product.dto.ProductRequest;
import com.sunyesle.wanted_market.product.dto.ProductResponse;
import com.sunyesle.wanted_market.global.exception.ErrorCodeException;
import com.sunyesle.wanted_market.global.exception.ProductErrorCode;
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
