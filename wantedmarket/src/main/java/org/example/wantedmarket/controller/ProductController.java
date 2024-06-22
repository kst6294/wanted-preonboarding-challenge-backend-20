package org.example.wantedmarket.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.common.DataResponse;
import org.example.wantedmarket.dto.common.ErrorResponse;
import org.example.wantedmarket.dto.common.ApiResponse;
import org.example.wantedmarket.dto.product.ProductCreateRequest;
import org.example.wantedmarket.dto.product.ProductUpdateRequest;
import org.example.wantedmarket.dto.user.CustomUserDetails;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.repository.jpa.UserJpaRepository;
import org.example.wantedmarket.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final UserJpaRepository userRepository;

    /* 제품 등록 */
    @PostMapping
    public ApiResponse registerProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid ProductCreateRequest request) {
        log.info("제품 등록 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponse<>(productService.saveProduct(userId, request));
        } catch (CustomException exception) {
            return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        }

        // todo : jwt 만료와 같은 custom 하지 않은 예외도 처리
    }

    /* 제품 전체 목록 조회
    *  비회원 가능
    */
    @GetMapping
    public ApiResponse getAllProductList() {
        log.info("제품 전체 목록 조회 api");

        try {
            return new DataResponse<>(productService.findAllProductList());
        } catch (CustomException exception) {
            return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 제품 상세 조회
    *  비회원 가능
    */
    @GetMapping("/{productId}")
    public ApiResponse getDetailProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long productId) {
        log.info("제품 상세 조회 api");

        try {
            if (customUserDetails != null) {
                String username = customUserDetails.getUsername();
                Long userId = userRepository.findByUsername(username).getId();
                return new DataResponse<>(productService.findDetailProductWithTransaction(userId, productId));
            }
            return new DataResponse<>(productService.findDetailProduct(productId));
        } catch (CustomException exception) {
            return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        }
    }

    // 내가 등록한 제품 조회
    @GetMapping("/me")
    public ApiResponse getMyProductList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("내가 등록한 제품 조회 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponse<>(productService.findMyProductList(userId));
        } catch (CustomException exception) {
            return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 구매한 제품 목록 조회 */
    @GetMapping("/completed")
    public ApiResponse getOrderedProductList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("구매한 제품 목록 조회 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponse<>(productService.findOrderedProductList(userId));
        } catch (CustomException exception) {
            return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 예약중인 제품 목록 조회 */
    @GetMapping("/reserved")
    public ApiResponse getReservedProductList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("예약중인 제품 목록 조회 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponse<>(productService.findReservedProductList(userId));
        } catch (CustomException exception) {
            return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 제품 가격 수정 */
    @PatchMapping("/price")
    public ApiResponse updateProductPrice(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody @Valid ProductUpdateRequest request) {
        log.info("제품 가격 수정 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponse<>(productService.modifyProductPrice(userId, request));
        } catch (CustomException exception) {
            return new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        }
    }

}
