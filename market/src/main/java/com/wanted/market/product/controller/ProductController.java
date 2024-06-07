package com.wanted.market.product.controller;

import com.wanted.market.product.dto.ProductRequestDto;
import com.wanted.market.product.dto.ProductResponseDto;
import com.wanted.market.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /* 제품 등록 */
    @PostMapping
    public ResponseEntity<ProductResponseDto> registProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.registProduct(productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    /* 제품 상세 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Integer id) {
        ProductResponseDto productResponseDto = productService.findById(id);
        return ResponseEntity.ok().body(productResponseDto);
    }

    /* 제품 목록 조회 */
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProductList() {
        List<ProductResponseDto> productResponseDtos = productService.findAll();
        return ResponseEntity.ok().body(productResponseDtos);
    }
}
