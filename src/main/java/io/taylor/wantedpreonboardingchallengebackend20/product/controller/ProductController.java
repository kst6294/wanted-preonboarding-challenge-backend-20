package io.taylor.wantedpreonboardingchallengebackend20.product.controller;

import io.taylor.wantedpreonboardingchallengebackend20.product.entity.Product;
import io.taylor.wantedpreonboardingchallengebackend20.product.model.request.ProductRequest;
import io.taylor.wantedpreonboardingchallengebackend20.product.model.response.ProductResponse;
import io.taylor.wantedpreonboardingchallengebackend20.product.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getProductList() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @PostMapping("")
    public ResponseEntity<ProductResponse> postProduct(@RequestHeader("Authorization") String authorization, @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.postProduct(authorization, request));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String productId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.getProductById(Long.parseLong(productId)));
    }

    @PatchMapping("/{productId}/approve")
    public ResponseEntity<Object> approveProduct(@RequestHeader HttpHeaders header, String productId) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
