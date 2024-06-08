package com.chaewon.wanted.domain.product.service;

import com.chaewon.wanted.domain.product.dto.request.ModifyRequestDto;
import com.chaewon.wanted.domain.product.dto.response.ProductDetailResponseDto;
import com.chaewon.wanted.domain.product.dto.response.ProductResponseDto;
import com.chaewon.wanted.domain.product.dto.request.RegisterRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    void registerProduct(String email, RegisterRequestDto registrationDto);
    Page<ProductResponseDto> listProducts(Pageable pageable);
    ProductDetailResponseDto getProductDetails(Long productId);
    void modifyProduct(String email, Long productId, ModifyRequestDto modifyRequestDto);
}
