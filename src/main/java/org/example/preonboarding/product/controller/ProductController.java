package org.example.preonboarding.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.common.enums.ResultCode;
import org.example.preonboarding.common.payload.Api;
import org.example.preonboarding.product.exception.ProductNotFoundException;
import org.example.preonboarding.product.model.payload.request.ProductCreateRequest;
import org.example.preonboarding.product.model.payload.response.ProductResponse;
import org.example.preonboarding.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<List<ProductResponse>>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(productService.getProducts())
                        .build()
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        productService.getProductById(productId);
        return null;
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<ProductResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(productService.createProduct(productCreateRequest))
                        .build()
        );
    }

    // TODO
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductCreateRequest product) {
        productService.updateProduct(productId, product);
        return null;
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<ProductResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(productService.deleteProduct(productId))
                        .build()
        );
    }
}
