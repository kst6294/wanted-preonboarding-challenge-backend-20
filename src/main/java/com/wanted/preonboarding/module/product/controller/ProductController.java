package com.wanted.preonboarding.module.product.controller;


import com.wanted.preonboarding.module.common.payload.ApiResponse;
import com.wanted.preonboarding.module.product.dto.CreateProduct;
import com.wanted.preonboarding.module.product.service.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ProductController {

    private final ProductQueryService productQueryService;

    @PreAuthorize("hasAnyAuthority('NORMAL')")
    @PostMapping("/product")
    public ResponseEntity<ApiResponse<Void>> createProduct(@RequestBody @Validated CreateProduct createProduct) {

        return null;
    }


}
