package com.challenge.market.web.product.controller;

import com.challenge.market.web.product.dto.ProductResponse;
import com.challenge.market.domain.product.entity.Product;
import com.challenge.market.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    /**
     * 제품 전채 목록 조회
     *
     * @return ResponseEntity<List<ProductResponse>>
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getList(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable() final Long id ){
        log.info("id={}",id);
        return ResponseEntity.ok(Product.of(productService.get(id)));
    }






}
