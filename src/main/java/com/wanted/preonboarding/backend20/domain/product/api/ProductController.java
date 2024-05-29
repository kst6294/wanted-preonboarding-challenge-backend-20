package com.wanted.preonboarding.backend20.domain.product.api;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.product.application.ProductService;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductDetailDto;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductOutlineDto;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductRequestDto;
import com.wanted.preonboarding.backend20.global.auth.AuthMember;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequestDto dto, @AuthMember Member member) {
        if(member == null) throw new CustomException(ErrorCode.AUTHENTICATION_ERROR);
        productService.createProduct(dto, member);
        return ResponseEntity.ok().body("완료");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestParam Long id, @RequestBody @Valid ProductRequestDto dto, @AuthMember Member member) {
        if(member == null) throw new CustomException(ErrorCode.AUTHENTICATION_ERROR);
        productService.updateProduct(id, dto, member);
        return ResponseEntity.ok().body("완료");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductOutlineDto>> searchAllProducts(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productService.searchAllProducts(pageable));
    }

    @GetMapping
    public ResponseEntity<ProductDetailDto> searchProductById(@RequestParam Long id) {
        return ResponseEntity.ok(productService.searchProductById(id));
    }
}
