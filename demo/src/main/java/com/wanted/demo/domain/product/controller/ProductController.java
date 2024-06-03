package com.wanted.demo.domain.product.controller;

import com.wanted.demo.domain.product.dto.request.ProductPurchaseRequestDTO;
import com.wanted.demo.domain.product.dto.request.ProductRegisterRequestDTO;
import com.wanted.demo.domain.product.dto.response.ProductDetailResponseDTO;
import com.wanted.demo.domain.product.dto.response.ProductPurchaseResponseDTO;
import com.wanted.demo.domain.product.dto.response.ProductRegisterResponseDTO;
import com.wanted.demo.domain.product.dto.response.ProductResponseDTO;
import com.wanted.demo.domain.product.service.ProductService;
import com.wanted.demo.global.auth.AuthUser;
import com.wanted.demo.global.auth.OptionalAuthUser;
import com.wanted.demo.global.util.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    //상품 등록
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerProduct(@AuthUser Long userId, @Valid @RequestBody ProductRegisterRequestDTO productRegisterRequestDTO){
        ProductRegisterResponseDTO productRegisterResponseDTO = productService.registerProduct(productRegisterRequestDTO,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(productRegisterResponseDTO, "상품 등록 성공"));
    }


    //비회원 + 회원 모든 상품 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<?>> findAllProducts(){
        List<ProductResponseDTO> allProducts = productService.findAllProducts();

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(allProducts,"모든 상품 조회 성공"));
    }


    //상품 상세 조회(구매자와 판매자는 거래내역 확인, 비회원은 상세정보)
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> findProductDetail(@PathVariable(name = "id") Long id, @OptionalAuthUser Long userId){
        ProductDetailResponseDTO productDetailResponseDTO = productService.findProduct(userId, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(productDetailResponseDTO, "상품 조회 성공"));
    }

    //상품 구매
    @PostMapping("/purchase")
    public ResponseEntity<ApiResponse<?>> purchaseProduct(@Valid @RequestBody ProductPurchaseRequestDTO productPurchaseRequestDTO, @AuthUser Long userId){

        ProductPurchaseResponseDTO productPurchaseResponseDTO = productService.purchaseProduct(productPurchaseRequestDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(productPurchaseResponseDTO,"상품 구매 완료"));
    }


}
