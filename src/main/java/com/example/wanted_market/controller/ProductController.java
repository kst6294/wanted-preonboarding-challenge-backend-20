package com.example.wanted_market.controller;

import com.example.wanted_market.dto.request.ProductRegesterRequestDto;
import com.example.wanted_market.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
