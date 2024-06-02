package com.wanted.demo.domain.product.controller;

import com.wanted.demo.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    //상품 등록
//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<?>> registerProduct(@OptionalAuthUser Long userId, @Valid @RequestBody ProductRegisterRequestDTO productRegisterRequestDTO){
//
//    }
}
