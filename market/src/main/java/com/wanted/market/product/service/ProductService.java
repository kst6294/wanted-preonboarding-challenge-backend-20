package com.wanted.market.product.service;

import com.wanted.market.product.dto.ProductResponseDto;
import com.wanted.market.product.dto.ProductRequestDto;

import java.util.List;


public interface ProductService {
    ProductResponseDto registProduct(ProductRequestDto productRequestDto);

    ProductResponseDto findById(Integer productId);

    List<ProductResponseDto> findAll();
}
