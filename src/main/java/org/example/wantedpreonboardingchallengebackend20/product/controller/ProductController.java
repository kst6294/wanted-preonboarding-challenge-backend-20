package org.example.wantedpreonboardingchallengebackend20.product.controller;

import org.example.wantedpreonboardingchallengebackend20.common.model.CommonResponse;
import org.example.wantedpreonboardingchallengebackend20.product.entity.Product;
import org.example.wantedpreonboardingchallengebackend20.product.model.request.ProductRequest;
import org.example.wantedpreonboardingchallengebackend20.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<CommonResponse<Object>> getProductList() {
        List<Product> products = productService.getProducts();
        CommonResponse<Object> response;
        if (products != null) {
            response = CommonResponse.builder().result(true).data(products).build();
        } else {
            response = CommonResponse.builder().result(false).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse<Object>> postProduct(@RequestBody ProductRequest request) {
        Product product = productService.postProduct(new Product(request.getName(), request.getPrice()));
        CommonResponse<Object> response = CommonResponse.builder().result(true).data(product).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CommonResponse<Object>> getProduct(String productId) {
        Product product = productService.getProductById(Integer.parseInt(productId));
        CommonResponse<Object> response;
        if (product != null) {
            response = CommonResponse.builder().result(true).data(product).build();
        } else {
            response = CommonResponse.builder().result(false).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CommonResponse<Object>> buyProduct(String productId) {
        CommonResponse<Object> response = CommonResponse.builder().result(true).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
