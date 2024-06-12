package com.example.wanted_market.controller;

import com.example.wanted_market.dto.ResponseDto;
import com.example.wanted_market.dto.request.ProductRegesterRequestDto;
import com.example.wanted_market.dto.response.ProductDetailResponseDto;
import com.example.wanted_market.dto.response.ProductResponseDto;
import com.example.wanted_market.exception.CommonException;
import com.example.wanted_market.exception.ErrorCode;
import com.example.wanted_market.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    /** 제품 등록 **/
    @PostMapping("/register")
    public ResponseDto<?> register(@RequestBody ProductRegesterRequestDto productRegesterRequestDto,
                                   HttpSession session){
        Long userId = (Long)session.getAttribute("loginUser");
        if(userId == null)
            throw new CommonException(ErrorCode.LOGIN_REQUIRED);

        productService.register(userId, productRegesterRequestDto);
        return ResponseDto.ok("Product Registration success");
    }
    
    /** 제품 목록 조회 **/
    @GetMapping("/list")
    public ResponseDto<?> getList(HttpSession session) {
        List<ProductResponseDto> productList = productService.getList();
        return ResponseDto.ok(productList);
    }

    /** 제품 상세 조회 **/
    @GetMapping("/{productId}")
    public ResponseDto<?> getDetail(@PathVariable Long productId) {
        ProductDetailResponseDto productDetail = productService.getDetail(productId);
        return ResponseDto.ok(productDetail);
    }
}
