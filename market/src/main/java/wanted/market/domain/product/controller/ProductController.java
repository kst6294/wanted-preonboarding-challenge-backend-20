package wanted.market.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import wanted.market.domain.product.controller.dto.request.ProductRegisterRequest;
import wanted.market.domain.product.service.ProductService;
import wanted.market.domain.product.service.dto.response.ProductDetailResponse;
import wanted.market.domain.product.service.dto.response.ProductListResponse;
import wanted.market.global.dto.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/register")
    public ApiResponse<Long> registerProduct(@RequestBody ProductRegisterRequest request, @AuthenticationPrincipal UserDetails userDetail) {
        return ApiResponse.ok(productService.register(request.toService(userDetail.getUsername())));
    }

    @GetMapping("/list")
    public ApiResponse<List<ProductListResponse>> findProductList(@RequestParam("page") int page) {
        return ApiResponse.ok(productService.findProductList(page));
    }

    @GetMapping("/detail")
    public ApiResponse<ProductDetailResponse> findProductDetail(@RequestParam("productId") Long productId) {
        return ApiResponse.ok(productService.findProductDetail(productId));
    }
}
