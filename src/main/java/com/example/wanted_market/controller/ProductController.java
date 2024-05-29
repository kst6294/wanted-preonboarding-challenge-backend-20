package com.example.wanted_market.controller;

import com.example.wanted_market.dto.request.ProductRegesterRequestDto;
import com.example.wanted_market.dto.response.ProductDetailResponseDto;
import com.example.wanted_market.dto.response.ProductResponseDto;
import com.example.wanted_market.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    /** 제품 등록 **/
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ProductRegesterRequestDto productRegesterRequestDto,
                                      HttpSession session){
        try{
            Long userId = (Long)session.getAttribute("loginUser");
            if(userId == null)
                throw new IllegalArgumentException("로그인이 필요합니다.");

            productService.register(userId, productRegesterRequestDto);
            return ResponseEntity.ok("Product Registration success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Product Registration failed: "+e.getMessage());
        }
    }
    
    /** 제품 목록 조회 **/
    @GetMapping("/list")
    public ResponseEntity<?> getList(HttpSession session) {
        try {
            List<ProductResponseDto> productList = productService.getList();
            return ResponseEntity.ok(productList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Product List failed: "+e.getMessage());
        }
    }

    /** 제품 상세 조회 **/
    @GetMapping("/{productId}")
    public ResponseEntity<?> getDetail(@PathVariable Long productId) {
        try{
            ProductDetailResponseDto productDetail = productService.getDetail(productId);
            return ResponseEntity.ok(productDetail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Product List failed: "+e.getMessage());
        }
    }
}
