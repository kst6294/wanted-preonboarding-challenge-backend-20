package org.example.wantedmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.product.ProductCreateDto;
import org.example.wantedmarket.dto.product.ProductDetailDto;
import org.example.wantedmarket.dto.product.ProductInfoDto;
import org.example.wantedmarket.dto.user.CustomUserDetails;
import org.example.wantedmarket.model.User;
import org.example.wantedmarket.repository.UserRepository;
import org.example.wantedmarket.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ProductCreateDto.Response registerProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ProductCreateDto.Request request) {
        log.info("제품 등록 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        return productService.saveProduct(userId, request);
    }

    /* 제품 전체 목록 조회
    *  비회원 가능
    */
    @GetMapping
    public List<ProductInfoDto> getAllProductList() {
        log.info("제품 전체 목록 조회 api");

        return productService.findAllProductList();
    }

    /* 제품 상세 조회
    *  비회원 가능
    */
    @GetMapping("/{productId}")
    public ProductDetailDto getDetailProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long productId) {
        log.info("제품 상세 조회 api");

        System.out.println("customDetail: " + customUserDetails);
        if (customUserDetails != null) {
            String username = customUserDetails.getUsername();
            Long userId = userRepository.findByUsername(username).getId();
            System.out.println("customDetail - userId: " + userId);
            return productService.findDetailProductWithTransaction(userId, productId);
        }

        return productService.findDetailProduct(productId);
    }

    /* 구매한 제품 목록 조회 */
    @GetMapping("/completed")
    public List<ProductInfoDto> getOrderedProductList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("구매한 제품 목록 조회 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        return productService.findOrderedProductList(userId);
    }

    /* 예약중인 제품 목록 조회 */
    @GetMapping("/reserved")
    public List<ProductInfoDto> getReservedProductList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("예약중인 제품 목록 조회 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        return productService.findReservedProductList(userId);
    }

}
