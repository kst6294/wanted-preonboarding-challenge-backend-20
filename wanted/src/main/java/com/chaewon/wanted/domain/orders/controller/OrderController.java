package com.chaewon.wanted.domain.orders.controller;

import com.chaewon.wanted.common.PageResponse;
import com.chaewon.wanted.common.ResponseDto;
import com.chaewon.wanted.domain.orders.dto.request.OrderRequestDto;
import com.chaewon.wanted.domain.orders.dto.response.BuyerPurchaseHistoryResponseDto;
import com.chaewon.wanted.domain.orders.service.OrderService;
import com.chaewon.wanted.domain.orders.dto.response.SalesApprovalListResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseDto> createOrder(@AuthenticationPrincipal User user,
                                                   @Valid @RequestBody OrderRequestDto orderRequestDto) {
        orderService.createOrder(user.getUsername(), orderRequestDto);
        return ResponseDto.of(HttpStatus.OK, "제품 거래를 시작했습니다.");
    }

    @GetMapping("/my-purchases")
    public ResponseEntity<PageResponse<BuyerPurchaseHistoryResponseDto>> getMyPurchases(@AuthenticationPrincipal User user,
                                                                                        @RequestParam(name = "orderStatus", required = false) String orderStatus,
                                                                                        @PageableDefault(size = 5) Pageable pageable) {
        Page<BuyerPurchaseHistoryResponseDto> myPurchases = orderService.getMyPurchasesConditional(user.getUsername(), orderStatus, pageable);
        return ResponseEntity.ok(new PageResponse<>(myPurchases));
    }

    @GetMapping("/my-sales")
    public ResponseEntity<PageResponse<SalesApprovalListResponseDto>> findMySales(@AuthenticationPrincipal User user,
                                                                                  @PageableDefault(size = 5) Pageable pageable) {
        Page<SalesApprovalListResponseDto> mySales = orderService.findMySales(user.getUsername(), pageable);
        return ResponseEntity.ok(new PageResponse<>(mySales));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<ResponseDto> approveOrder(@AuthenticationPrincipal User user,
                                                     @PathVariable(name = "orderId") Long orderId) {
        orderService.approveOrder(user.getUsername(), orderId);
        return ResponseDto.of(HttpStatus.OK, "판매 승인 처리 되었습니다.");
    }

}
