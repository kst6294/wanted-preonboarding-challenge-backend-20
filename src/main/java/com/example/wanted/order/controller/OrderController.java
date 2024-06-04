package com.example.wanted.order.controller;

import com.example.wanted.order.domain.OrderCreate;
import com.example.wanted.order.service.OrderService;
import com.example.wanted.order.service.response.OrderResponse;
import com.example.wanted.user.controller.response.TokenResponse;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v0/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    @Operation(summary = "주문", description = "구매요청 주문을 생성합니다", security = @SecurityRequirement(name="bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "주문 성공",
            content = @Content(schema = @Schema(implementation = OrderResponse.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"seller\": {\"id\": 1,\"account\": \"test2\",\"name\": \"홍길동\"},\"buyer\": {\"id\": 2,\"account\": \"buyer\",\"name\": \"정우성\"},\"price\": 1000,\"productId\": 1,\"productName\": \"돼지바\",\"status\": \"REQUEST\"}"
                    )

            ))
    public ResponseEntity<OrderResponse> order(@Valid @RequestBody OrderCreate orderCreate, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(orderService.order(orderCreate, userId));
    }

    @GetMapping("")
    @Operation(summary = "내 주문 내역 확인", description = "내 주문 내역을 조회합니다", security = @SecurityRequirement(name="bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "주문 조회 성공",
            content = @Content(schema = @Schema(implementation = OrderResponse.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"seller\": {\"id\": 1,\"account\": \"test2\",\"name\": \"홍길동\"},\"buyer\": {\"id\": 2,\"account\": \"buyer\",\"name\": \"정우성\"},\"price\": 1000,\"productId\": 1,\"productName\": \"돼지바\",\"status\": \"REQUEST\"}"
                    )

            ))
    public ResponseEntity<List<OrderResponse>> getByUserId(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(orderService.getByUserId(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "주문 상세 조회", description = "Order Id로 주문을 상세 조회합니다.", security = @SecurityRequirement(name="bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "주문 상세 조회 성공",
            content = @Content(schema = @Schema(implementation = OrderResponse.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"seller\": {\"id\": 1,\"account\": \"test2\",\"name\": \"홍길동\"},\"buyer\": {\"id\": 2,\"account\": \"buyer\",\"name\": \"정우성\"},\"price\": 1000,\"productId\": 1,\"productName\": \"돼지바\",\"status\": \"REQUEST\"}"
                    )

            ))
    public ResponseEntity<OrderResponse> getById(@PathVariable("id") long id) {
        return ResponseEntity
                .ok()
                .body(orderService.getById(id));
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "제품 구매 내역 조회", description = "product Id로 제품 구매/판매 내역 리스트 조회합니다.", security = @SecurityRequirement(name="bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "제품 구매 내역 조회",
            content = @Content(schema = @Schema(implementation = OrderResponse.class),
                    examples = @ExampleObject(
                            value = "[{\"id\": 1,\"seller\": {\"id\": 1,\"account\": \"test2\",\"name\": \"홍길동\"},\"buyer\": {\"id\": 2,\"account\": \"buyer\",\"name\": \"정우성\"},\"price\": 1000,\"productId\": 1,\"productName\": \"돼지바\",\"status\": \"REQUEST\"}]"
                    )

            ))
    public ResponseEntity<List<OrderResponse>> getByUserIdAndProductId(
            @PathVariable("productId") long productId,
            Principal principal
    ) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(orderService.getByUserIdAndProductId(userId, productId));
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "판매 승인", description = "Order Id로 판매자는 판매를 승인합니다", security = @SecurityRequirement(name="bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "판매 승인 성공",
            content = @Content(schema = @Schema(implementation = OrderResponse.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"seller\": {\"id\": 1,\"account\": \"test2\",\"name\": \"홍길동\"},\"buyer\": {\"id\": 2,\"account\": \"buyer\",\"name\": \"정우성\"},\"price\": 1000,\"productId\": 1,\"productName\": \"돼지바\",\"status\": \"REQUEST\"}"
                    )

            ))
    public ResponseEntity<OrderResponse> approve(
            @PathVariable("id") long id,
            Principal principal
    ) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(orderService.approve(id, userId));
    }

    @PostMapping("/{id}/confirmation")
    @Operation(summary = "구매 확정", description = "Order Id로 구매자는 구매를 확정합니다", security = @SecurityRequirement(name="bearerAuth"))
    @ApiResponse(
            responseCode = "200",
            description = "구매 확정 성공",
            content = @Content(schema = @Schema(implementation = OrderResponse.class),
                    examples = @ExampleObject(
                            value = "{\"id\": 1,\"seller\": {\"id\": 1,\"account\": \"test2\",\"name\": \"홍길동\"},\"buyer\": {\"id\": 2,\"account\": \"buyer\",\"name\": \"정우성\"},\"price\": 1000,\"productId\": 1,\"productName\": \"돼지바\",\"status\": \"REQUEST\"}"
                    )

            ))
    public ResponseEntity<OrderResponse> confirmation(
            @PathVariable("id") long id,
            Principal principal
    ) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity
                .ok()
                .body(orderService.confirmation(id, userId));
    }
}
