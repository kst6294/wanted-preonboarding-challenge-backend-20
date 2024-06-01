package com.wanted.preonboarding.domain.product.controller;

import com.wanted.preonboarding.domain.product.dto.request.AddProductRequest;
import com.wanted.preonboarding.domain.product.dto.response.ProductResponse;
import com.wanted.preonboarding.domain.product.service.ProductService;
import com.wanted.preonboarding.global.auth.AuthUser;
import com.wanted.preonboarding.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> addProduct(@AuthUser Long userId, @RequestBody @Valid AddProductRequest addProductRequest) {
        productService.addProduct(userId, addProductRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccess("상품 등록이 완료되었습니다."));
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<ProductResponse> productResponseList = productService.getProducts();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccess(productResponseList, "상품 리스트 조회가 완료되었습니다."));
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProducts(@PathVariable Long productId) {
        ProductResponse productResponse = productService.getProduct(productId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.createSuccess(productResponse, "상품상세 조회가 완료되었습니다."));
    }
}

