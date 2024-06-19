package com.wanted.market.product.service;

import com.wanted.market.product.dto.ProductDetailResponseDto;
import com.wanted.market.product.dto.ProductResponseDto;
import com.wanted.market.product.dto.ProductRequestDto;

import java.util.List;


public interface ProductService {
    ProductResponseDto registerProduct(String email, ProductRequestDto productRequestDto);

    ProductResponseDto findById(String email, Integer productId);

    // 제품 상세 조회
    ProductDetailResponseDto findDetailProductById(String email, Integer id);

    List<ProductResponseDto> findAll();
}
