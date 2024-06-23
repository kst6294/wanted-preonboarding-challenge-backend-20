package kr.co.wanted.market.product.controller;

import jakarta.validation.Valid;
import kr.co.wanted.market.common.global.dto.ApiResult;
import kr.co.wanted.market.common.global.dto.PageInfo;
import kr.co.wanted.market.common.global.dto.PageResult;
import kr.co.wanted.market.product.dto.ProductDetail;
import kr.co.wanted.market.product.dto.ProductRegistration;
import kr.co.wanted.market.product.dto.ProductSimple;
import kr.co.wanted.market.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController implements ProductControllerDocs {

    private final ProductService productService;


    @Override
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResult<ProductRegistration>> registerProduct(@RequestBody @Valid ProductRegistration productRegistration) {

        ProductRegistration registered = productService.registerProduct(productRegistration);

        return ResponseEntity.ok(new ApiResult<>(registered));
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<ProductDetail>> searchProductDetail(@PathVariable Long id) {

        ProductDetail productDetail = productService.searchProductDetail(id);

        return ResponseEntity.ok(new ApiResult<>(productDetail));
    }


    @Override
    @GetMapping
    public ResponseEntity<ApiResult<PageResult<ProductSimple>>> searchProductList(@ModelAttribute PageInfo pageInfo) {

        PageResult<ProductSimple> products = productService.searchProducts(pageInfo);

        return ResponseEntity.ok(new ApiResult<>(products));
    }

}
