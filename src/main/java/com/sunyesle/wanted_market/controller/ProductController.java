package com.sunyesle.wanted_market.controller;

import com.sunyesle.wanted_market.dto.ProductDetailResponse;
import com.sunyesle.wanted_market.dto.ProductRequest;
import com.sunyesle.wanted_market.dto.ProductResponse;
import com.sunyesle.wanted_market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody ProductRequest request){
        ProductResponse response = productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable Long id){
        ProductDetailResponse response = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
