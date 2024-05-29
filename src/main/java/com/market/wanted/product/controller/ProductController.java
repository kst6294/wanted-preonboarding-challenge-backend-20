package com.market.wanted.product.controller;

import com.market.wanted.product.dto.ProductDto;
import com.market.wanted.product.entity.Product;
import com.market.wanted.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> productList(HttpSession session) {
        List<ProductDto> productDtos = productService.findAll();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> productDetail(@PathVariable("productId") Long productId) {
        ProductDto findProductDto = productService.findDtoById(productId);
        return ResponseEntity.ok(findProductDto);
    }

}
