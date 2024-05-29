package com.exception_study.controller;

import com.exception_study.dto.ProductDto;
import com.exception_study.dto.request.RegisterRequest;
import com.exception_study.dto.response.ResponseDto;
import com.exception_study.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;


    @GetMapping("/list")
    public ResponseDto<List<ProductDto>> list(){
        List<ProductDto> result = productService.getList();
        return ResponseDto.success(result);
    }

    @PostMapping("/register")
    public ResponseDto<Void> register(@RequestBody RegisterRequest request, @AuthenticationPrincipal String userId){
        productService.register(request,userId);
        return ResponseDto.success();
    }

    @GetMapping("/{id}/details")
    public ResponseDto<ProductDto> details(@PathVariable int id){
        ProductDto result = productService.getDetails(id);
        return ResponseDto.success(result);
    }

    @PostMapping("/{id}/details/buy")
    public ResponseDto<ProductDto> buy(@PathVariable int id, @AuthenticationPrincipal String userId){
        ProductDto result = productService.buy(id, userId);
        return ResponseDto.success(result);
    }






}
