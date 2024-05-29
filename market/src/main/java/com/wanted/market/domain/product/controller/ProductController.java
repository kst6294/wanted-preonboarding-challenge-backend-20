package com.wanted.market.domain.product.controller;

import com.wanted.market.domain.product.request.RegisterRequest;
import com.wanted.market.domain.product.response.DetailResponse;
import com.wanted.market.domain.product.response.ListResponse;
import com.wanted.market.domain.product.service.ProductService;
import com.wanted.market.global.common.code.ResponseCode;
import com.wanted.market.global.common.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/reigster")
    public BaseResponse register(@RequestBody @Valid RegisterRequest request, BindingResult bindingResult, HttpServletRequest httpRequest) {

        if(bindingResult.hasErrors()) {
            return new BaseResponse(ResponseCode.BAD_REQUEST);
        }

        productService.register(request, httpRequest);

        return new BaseResponse(ResponseCode.SUCCESS);
    }

    @GetMapping("/list/{page}")
    public ListResponse getList(@PathVariable("page") int page) {

        if (page < 0) {
            return new ListResponse(ResponseCode.BAD_REQUEST);
        }

        return new ListResponse(ResponseCode.SUCCESS, productService.getList(page - 1));
    }

    @GetMapping("/{product_no}")
    public DetailResponse getProduct(@PathVariable("product_no") long no) {

        if(no < 0) {
            return new DetailResponse(ResponseCode.BAD_REQUEST);
        }

        return new DetailResponse(ResponseCode.SUCCESS, productService.getProduct(no));
    }
}
