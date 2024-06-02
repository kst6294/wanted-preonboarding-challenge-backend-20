package com.wanted.wantedpreonboardingchallengebackend20.domain.product.controller;

import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.request.RegistProductRequestDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response.ListProductResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response.RegistProductResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.entity.Product;
import com.wanted.wantedpreonboardingchallengebackend20.domain.product.service.ProductService;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.controller.UserController;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response.SuccessResponseDto;
import com.wanted.wantedpreonboardingchallengebackend20.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final UserController user;
    private final ProductService productService;
    @PostMapping("/regist-product")
    public ResponseEntity<SuccessResponseDto> registProduct(@RequestBody RegistProductRequestDto requestDto){
        User loggedInUser=user.getLoggedInUser();
        System.out.println(loggedInUser);
        if(loggedInUser==null){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(SuccessResponseDto.<User>builder()
                            .statusCode(HttpStatus.UNAUTHORIZED)
                            .resultMessage("로그인 필요 제품 등록 실패")
                            .build());
        }
        RegistProductResponseDto responseDto=productService.registProduct(loggedInUser,requestDto);
        loggedInUser.getProductList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponseDto.<RegistProductResponseDto>builder()
                        .statusCode(HttpStatus.OK)
                        .resultMessage("제품 등록 성공")
                        .data(responseDto)
                        .build());
    }
    @GetMapping("")
    public ListProductResponseDto listProduct(){
        return productService.listProduct();
    }
    @GetMapping("/buy/{productId}")
    public ResponseEntity<SuccessResponseDto> buyProduct(@PathVariable Long productId){
        User loggedInUser=user.getLoggedInUser();
        boolean response=productService.buyProduct(loggedInUser,productId);
        if(response){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseDto.builder().statusCode(HttpStatus.OK)
                            .resultMessage("거래를 진행합니다.")
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponseDto.builder().statusCode(HttpStatus.OK)
                        .resultMessage("거래 진행 불가")
                        .build());
    }
}
