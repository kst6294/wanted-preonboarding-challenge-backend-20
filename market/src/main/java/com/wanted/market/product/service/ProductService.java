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

    // 내가 구매한 제품 조회
    List<ProductResponseDto> findMyProductByMemberId(String email);

    // 예약중인 제품 조회
    List<ProductResponseDto> findReservedProduct(String email);
}
