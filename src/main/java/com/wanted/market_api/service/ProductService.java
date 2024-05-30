package com.wanted.market_api.service;

import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.request.product.ProductCreateRequestDto;
import com.wanted.market_api.dto.response.product.PagingProductResponseDto;
import com.wanted.market_api.dto.response.product.ProductResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ApiResponse createProduct(ProductCreateRequestDto productCreateRequestDto, long memberId);
    ProductResponseDto getProduct(long productId);
    PagingProductResponseDto getProducts(Pageable pageable);
}
