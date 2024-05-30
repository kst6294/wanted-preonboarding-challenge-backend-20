package com.sunyesle.wanted_market.controller;

import com.sunyesle.wanted_market.dto.ProductRequest;
import com.sunyesle.wanted_market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestBody ProductRequest request){
        productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
