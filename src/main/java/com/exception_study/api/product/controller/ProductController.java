package com.exception_study.api.product.controller;

import com.exception_study.api.product.dto.ProductDto;
import com.exception_study.api.product.service.ProductService;
import com.exception_study.api.product.dto.response.DetailsWithHistoryResponse;
import com.exception_study.api.user_account.dto.request.RegisterRequest;
import com.exception_study.global.dto.response.ResponseDto;
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

    @GetMapping("/{id}/details/authenticated")
    public ResponseDto<DetailsWithHistoryResponse> details(@PathVariable int id, @AuthenticationPrincipal String userId){
        DetailsWithHistoryResponse result = productService.getDetails(id,userId);
        return ResponseDto.success(result);
    }

}
