package com.wanted.preonboarding.domain.product.controller;

import com.wanted.preonboarding.domain.product.dto.request.AddProductRequest;
import com.wanted.preonboarding.domain.product.service.ProductService;
import com.wanted.preonboarding.global.auth.AuthUser;
import com.wanted.preonboarding.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

