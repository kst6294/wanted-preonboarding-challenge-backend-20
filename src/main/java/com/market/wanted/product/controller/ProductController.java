package com.market.wanted.product.controller;

import com.market.wanted.product.dto.AddProduct;
import com.market.wanted.product.dto.ProductDto;
import com.market.wanted.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@Validated @RequestBody AddProduct addProduct, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        productService.addProduct(addProduct, memberId);
        return ResponseEntity.ok("제품이 등록 되었습니다.");
    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> productList(HttpSession session) {
        List<ProductDto> productDtos = productService.findAll();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("productId") Long productId) {
        ProductDto findProductDto = productService.findDtoById(productId);
        return ResponseEntity.ok(findProductDto);
    }

}
