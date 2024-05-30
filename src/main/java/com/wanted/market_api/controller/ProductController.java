package com.wanted.market_api.controller;

import com.wanted.market_api.dto.ApiResponse;
import com.wanted.market_api.dto.request.product.ProductCreateRequestDto;
import com.wanted.market_api.dto.response.product.PagingProductResponseDto;
import com.wanted.market_api.dto.response.product.ProductResponseDto;
import com.wanted.market_api.service.ProductService;
import com.wanted.market_api.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping("/v1/product/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductCreateRequestDto productCreateRequestDto) {
        return ResponseEntity.ok(productService.createProduct(productCreateRequestDto, AuthUtil.checkAuth()));
    }

    @GetMapping("/v1/product")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProduct(@RequestParam(value = "productId") Long productId) {
        return ResponseEntity.ok(new ApiResponse<>(productService.getProduct(productId)));
    }

    @GetMapping("/v1/product/list")
    public ResponseEntity<ApiResponse<PagingProductResponseDto>> getProduct(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(new ApiResponse<>(productService.getProducts(pageable)));
    }
}
