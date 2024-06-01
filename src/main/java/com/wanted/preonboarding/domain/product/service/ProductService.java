package com.wanted.preonboarding.domain.product.service;

import com.wanted.preonboarding.domain.product.dto.request.AddProductRequest;
import com.wanted.preonboarding.domain.product.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    void addProduct(Long userId, AddProductRequest addProductRequest);

    List<ProductResponse> getProducts();
}
