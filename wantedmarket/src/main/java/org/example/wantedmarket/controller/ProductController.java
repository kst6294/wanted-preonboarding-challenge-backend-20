package org.example.wantedmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.common.DataResponseDto;
import org.example.wantedmarket.dto.common.ErrorResponseDto;
import org.example.wantedmarket.dto.common.ResponseDto;
import org.example.wantedmarket.dto.product.ProductCreateDto;
import org.example.wantedmarket.dto.product.ProductDetailDto;
import org.example.wantedmarket.dto.product.ProductInfoDto;
import org.example.wantedmarket.dto.product.ProductUpdateDto;
import org.example.wantedmarket.dto.user.CustomUserDetails;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.repository.UserRepository;
import org.example.wantedmarket.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;

    /* 제품 등록 */
    @PostMapping
    public ResponseDto registerProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ProductCreateDto.Request request) {
        log.info("제품 등록 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>(productService.saveProduct(userId, request));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }

        // todo : jwt 만료와 같은 custom 하지 않은 예외도 처리
    }

    /* 제품 전체 목록 조회
    *  비회원 가능
    */
    @GetMapping
    public ResponseDto getAllProductList() {
        log.info("제품 전체 목록 조회 api");

        try {
            return new DataResponseDto<>(productService.findAllProductList());
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 제품 상세 조회
    *  비회원 가능
    */
    @GetMapping("/{productId}")
    public ResponseDto getDetailProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long productId) {
        log.info("제품 상세 조회 api");

        try {
            if (customUserDetails != null) {
                String username = customUserDetails.getUsername();
                Long userId = userRepository.findByUsername(username).getId();
                return new DataResponseDto<>(productService.findDetailProductWithTransaction(userId, productId));
            }
            return new DataResponseDto<>(productService.findDetailProduct(productId));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

    // 내가 등록한 제품 조회
    @GetMapping("/me")
    public ResponseDto getMyProductList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("내가 등록한 제품 조회 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>(productService.findMyProductList(userId));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 구매한 제품 목록 조회 */
    @GetMapping("/completed")
    public ResponseDto getOrderedProductList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("구매한 제품 목록 조회 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>(productService.findOrderedProductList(userId));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 예약중인 제품 목록 조회 */
    @GetMapping("/reserved")
    public ResponseDto getReservedProductList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("예약중인 제품 목록 조회 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>(productService.findReservedProductList(userId));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 제품 가격 수정 */
    @PatchMapping("/price")
    public ResponseDto updateProductPrice(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ProductUpdateDto.Request request) {
        log.info("제품 가격 수정 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>(productService.modifyProductPrice(userId, request));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

}
