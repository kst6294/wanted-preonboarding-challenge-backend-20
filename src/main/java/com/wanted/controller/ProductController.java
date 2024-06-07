package com.wanted.controller;

import com.wanted.dto.ListDto;
import com.wanted.dto.OrderDto;
import com.wanted.dto.ProductDto;
import com.wanted.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    /* 제품 등록 */
    @PostMapping("/join/product")
    public ResponseEntity<?> join(@RequestBody @Valid ProductDto productDto, HttpSession session){

        ProductDto savedProduct = productService.join(productDto, session);

        return ResponseEntity.ok(savedProduct);
    }

    /* 제품 목록 조회 */
    @GetMapping("/list/product")
    public ResponseEntity<List<ProductDto>> listProduct(){

        List<ProductDto> productDto = productService.listProudct();

        return ResponseEntity.ok(productDto);
    }

    /* 제품 구매 */
    @PostMapping("/purchase/product/{product_id}")
    public ResponseEntity<?> purchaseProduct(@PathVariable Long product_id, Long count, Long price, HttpSession session){

        OrderDto orderDto = productService.purchaseProduct(product_id, count, price, session);

        return ResponseEntity.ok(orderDto);
    }

    /* 제품 상세 정보 조회 */
    @GetMapping("/datail/product")
    public ResponseEntity<?> detailProduct(){

         ListDto listDtos = productService.detailProduct();

        return ResponseEntity.ok(listDtos);
    }


}
