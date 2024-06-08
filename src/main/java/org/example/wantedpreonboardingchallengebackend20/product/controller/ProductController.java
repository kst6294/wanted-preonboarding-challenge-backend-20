package org.example.wantedpreonboardingchallengebackend20.product.controller;

import org.example.wantedpreonboardingchallengebackend20.common.model.CommonResponse;
import org.example.wantedpreonboardingchallengebackend20.product.entity.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<CommonResponse<Object>> getProductList() {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse<Object>> addProduct(@RequestBody Product product) {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse<Object>> getProduct() {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CommonResponse<Object>> buyProduct(@RequestBody Product product) {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
