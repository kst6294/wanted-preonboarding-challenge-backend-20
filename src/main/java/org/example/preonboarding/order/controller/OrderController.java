package org.example.preonboarding.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.common.enums.ResultCode;
import org.example.preonboarding.common.payload.Api;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.util.MemberUtil;
import org.example.preonboarding.order.model.enums.OrderDivision;
import org.example.preonboarding.order.model.payload.request.OrderCreateRequest;
import org.example.preonboarding.order.model.payload.request.OrderSearchRequest;
import org.example.preonboarding.order.model.payload.response.OrderResponse;
import org.example.preonboarding.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final MemberUtil memberUtil;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        LocalDateTime orderedAt = LocalDateTime.now();

        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<OrderResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(orderService.createOrder(orderCreateRequest, orderedAt))
                        .build()
        );
    }

    // 판매자가 승인처리를 하는 것.
    @PostMapping("/approve/{orderId}")
    public ResponseEntity<?> approveOrder(@PathVariable("orderId") long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<OrderResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(orderService.approveOrder(orderId))
                        .build()
        );
    }

    // 로그인 user가 구매/판매하는 제품
    @GetMapping("/my-buy-order")
    public ResponseEntity<?> getMyBuyOrders(@RequestParam("orderDivision") OrderDivision orderDivision) {
        Member currentUser = memberUtil.getCurrentUser();
        OrderSearchRequest orderSearchRequest = OrderSearchRequest.builder()
                .member(currentUser)
                .orderDivision(orderDivision)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<List<OrderResponse>>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(orderService.searchOrder(orderSearchRequest))
                        .build()
        );
    }

    // 구매 확정
    @PostMapping("/complete/{orderId}")
    public ResponseEntity<?> completeOrder(@PathVariable("orderId") long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                Api.<OrderResponse>builder()
                        .resultCode(ResultCode.SUCCESS)
                        .data(orderService.completeOrder(orderId))
                        .build()
        );
    }

}
