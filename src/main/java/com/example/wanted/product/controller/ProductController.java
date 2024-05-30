package com.example.wanted.product.controller;

import com.example.wanted.product.domain.ProductCreate;
import com.example.wanted.product.service.ProductService;
import com.example.wanted.product.service.response.ProductResponse;
import com.example.wanted.user.domain.UserCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<Long> create(
            @RequestBody ProductCreate request,
            Principal principal
    ) {
        Long id = Long.parseLong(principal.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.register(request, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(
            @PathVariable("id") long id
    ) {
        return ResponseEntity
                .ok()
                .body(productService.getById(id));
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponse>> getList() {
        return ResponseEntity
                .ok()
                .body(productService.getList());
    }
}
