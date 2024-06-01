package com.wanted.preonboarding.domain.product.service;

import com.wanted.preonboarding.domain.product.dto.request.AddProductRequest;

public interface ProductService {
    void addProduct(Long userId, AddProductRequest addProductRequest);
}
