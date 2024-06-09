package org.example.wantedmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.product.ProductCreateDto;
import org.example.wantedmarket.dto.product.ProductDetailDto;
import org.example.wantedmarket.dto.product.ProductInfoDto;
import org.example.wantedmarket.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    /* 제품 등록 */
    @PostMapping
    public ProductCreateDto.Response registerProduct(Long userId, @RequestBody ProductCreateDto.Request request) {
        return productService.saveProduct(userId, request);
    }

    /* 제품 전체 목록 조회 */
    @GetMapping
    public List<ProductInfoDto> getAllProductList() {
        return productService.findAllProductList();
    }

    /* 제품 상세 조회 */
    @GetMapping("/{productId}")
    public ProductDetailDto getDetailProduct(@PathVariable Long productId) {
        return productService.findDetailProduct(productId);
    }

    /* 구매한 제품 목록 조회 */
    @GetMapping("/completed")
    public List<ProductInfoDto> getOrderedProductList(Long userId) {
        return productService.findOrderedProductList(userId);
    }

    /* 예약중인 제품 목록 조회 */
    @GetMapping("/reserved")
    public List<ProductInfoDto> getReservedProductList(Long userId) {
        return productService.findReservedProductList(userId);
    }

}
