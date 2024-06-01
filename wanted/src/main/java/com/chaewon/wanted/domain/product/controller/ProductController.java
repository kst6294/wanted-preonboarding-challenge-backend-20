package com.chaewon.wanted.domain.product.controller;

import com.chaewon.wanted.common.PageResponse;
import com.chaewon.wanted.common.ResponseDto;
import com.chaewon.wanted.domain.product.dto.response.ProductDetailResponseDto;
import com.chaewon.wanted.domain.product.dto.response.ProductResponseDto;
import com.chaewon.wanted.domain.product.dto.request.RegisterRequestDto;
import com.chaewon.wanted.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerProduct(@AuthenticationPrincipal User user,
                                                       @Valid @RequestBody RegisterRequestDto registerRequestDto) {
        productService.registerProduct(user.getUsername(), registerRequestDto);
        return ResponseDto.of(HttpStatus.OK, "제품등록을 완료했습니다.");
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponseDto>> listProducts(@PageableDefault(size = 5) Pageable pageable) {
        Page<ProductResponseDto> products = productService.listProducts(pageable);
        PageResponse<ProductResponseDto> response = new PageResponse<>(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponseDto> getProductDetails(@PathVariable(name = "productId") Long productId) {
        ProductDetailResponseDto productDetails = productService.getProductDetails(productId);
        return ResponseEntity.ok(productDetails);
    }

}
