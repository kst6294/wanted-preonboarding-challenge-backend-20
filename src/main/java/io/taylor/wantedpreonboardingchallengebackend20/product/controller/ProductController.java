package io.taylor.wantedpreonboardingchallengebackend20.product.controller;

import io.taylor.wantedpreonboardingchallengebackend20.product.entity.Product;
import io.taylor.wantedpreonboardingchallengebackend20.product.model.request.ProductRequest;
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
    public ResponseEntity<Object> getProductList() {
        List<Product> products = productService.getProducts();
        if (products != null) {
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("")
    public ResponseEntity<Object> postProduct(@RequestHeader HttpHeaders header, @RequestBody ProductRequest request) {
        Product product = productService.postProduct(new Product(request.getName(), request.getPrice()));
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> getProduct(@PathVariable String productId) {
        Product product = productService.getProductById(Long.parseLong(productId));
        if (product != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{productId}/approve")
    public ResponseEntity<Object> approveProduct(@RequestHeader HttpHeaders header, String productId) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
