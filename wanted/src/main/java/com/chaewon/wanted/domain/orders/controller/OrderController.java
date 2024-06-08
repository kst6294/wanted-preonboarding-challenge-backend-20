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
    /*
    TODO : 구매 요청과 판매자의 가격 변동이 거의 동시에 이루어지는 경우
    만약 구매 요청을 하는 시점과 판매자가 제품 가격을 변경하는 시점이 동시에 일어난다면, 실제로 어떤 가격이 주문에 적용될까?
    -> 구매자가 주문을 생성할 때 이미 보고 있는 가격으로 주문이 이루어지도록
    -> 트랜잭션 격리 수준을 활용하여
    -> 동시에 일어나면 구매자는 구매 시점에 이미 그 가격을 보고 구매한 거니까
    -> orderPrice는 변동되면 x / price는 변동 o
     */

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
        return ResponseDto.of(HttpStatus.OK, "판매자의 승인 여부가 반영 되었습니다.");
    }

    @PatchMapping("/{orderId}/confirm")
    public ResponseEntity<ResponseDto> confirmPurchase(@AuthenticationPrincipal User user,
                                                    @PathVariable(name = "orderId") Long orderId) {
        orderService.confirmPurchase(user.getUsername(), orderId);
        return ResponseDto.of(HttpStatus.OK, "구매 확정 처리 되었습니다.");
    }

}
