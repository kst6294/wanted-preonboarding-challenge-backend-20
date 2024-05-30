package com.wanted.market_api.service;

import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.request.product.ProductCreateRequestDto;
import com.wanted.market_api.dto.response.product.PagingProductResponseDto;
import com.wanted.market_api.dto.response.product.ProductResponseDto;
import com.wanted.market_api.entity.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ApiResponse createProduct(ProductCreateRequestDto productCreateRequestDto, long memberId);
    ProductResponseDto getProduct(long productId);
    PagingProductResponseDto getProducts(Pageable pageable);
    Product findWithMemberById(long productId);
}
