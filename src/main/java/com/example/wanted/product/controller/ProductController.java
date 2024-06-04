package com.example.wanted.product.controller;

import com.example.wanted.order.service.response.OrderResponse;
import com.example.wanted.product.domain.ProductCreate;
import com.example.wanted.product.service.ProductService;
import com.example.wanted.product.service.response.ProductResponse;
import com.example.wanted.user.domain.UserCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    @PostMapping("")
    @Operation(summary = "제품 등록", description = "판매할 제품을 등록합니다", security = @SecurityRequirement(name="bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "제품 등록 성공",
            content = @Content(
                    schema = @Schema(implementation = ProductResponse.class),
                    examples = @ExampleObject(
                        value = "{ \"id\": 1, \"seller\": { \"id\": 1, \"account\": \"test2\", \"name\": \"홍길동\" }, \"name\": \"돼지바\", \"price\": 1000, \"quantity\": 10, \"productSellingStatus\": \"SELLING\" }"
                )))
    public ResponseEntity<Long> create(
            @Valid @RequestBody ProductCreate request,
            Principal principal
    ) {
        Long id = Long.parseLong(principal.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.register(request, id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "제품 상세 조회", description = "Product Id로 제품 상세 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = ProductResponse.class),
            examples = @ExampleObject(
                    value = "{ \"id\": 1, \"seller\": { \"id\": 1, \"account\": \"test2\", \"name\": \"홍길동\" }, \"name\": \"돼지바\", \"price\": 1000, \"quantity\": 10, \"productSellingStatus\": \"SELLING\" }"

            )))
    public ResponseEntity<ProductResponse> getById(
            @PathVariable("id") long id
    ) {
        return ResponseEntity
                .ok()
                .body(productService.getById(id));
    }

    @GetMapping("")
    @Operation(summary = "제품 리스트 조회", description = "모든 제품들을 조회합니")
    @ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = ProductResponse.class),
                    examples = @ExampleObject(
                            value = "[{ \"id\": 1, \"seller\": { \"id\": 1, \"account\": \"test2\", \"name\": \"홍길동\" }, \"name\": \"돼지바\", \"price\": 1000, \"quantity\": 10, \"productSellingStatus\": \"SELLING\" }]"

            )))
    public ResponseEntity<List<ProductResponse>> getList() {
        return ResponseEntity
                .ok()
                .body(productService.getList());
    }
}
