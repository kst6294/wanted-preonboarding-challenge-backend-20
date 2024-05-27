package com.wanted.preonboarding.module.product.controller;


import com.wanted.preonboarding.module.common.dto.CustomSlice;
import com.wanted.preonboarding.module.common.payload.ApiResponse;
import com.wanted.preonboarding.module.product.core.Sku;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.filter.ItemFilter;
import com.wanted.preonboarding.module.product.service.ProductFindService;
import com.wanted.preonboarding.module.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProductController {

    private final ProductQueryService productQueryService;
    private final ProductFindService productFindService;

    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @PostMapping("/product")
    public ResponseEntity<ApiResponse<Sku>> createProduct(@RequestBody @Validated CreateProduct createProduct) {
        return ResponseEntity.ok(ApiResponse.success(productQueryService.createProduct(createProduct)));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<Sku>> fetchProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(ApiResponse.success(productFindService.fetchProduct(productId)));
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<CustomSlice<Sku>>> fetchProduct(@ModelAttribute ItemFilter filter, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(productFindService.fetchProducts(filter, pageable)));
    }


}
