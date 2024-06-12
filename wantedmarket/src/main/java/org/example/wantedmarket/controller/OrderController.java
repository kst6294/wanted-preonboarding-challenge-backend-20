package org.example.wantedmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.order.OrderCreateDto;
import org.example.wantedmarket.dto.order.OrderInfoDto;
import org.example.wantedmarket.dto.user.CustomUserDetails;
import org.example.wantedmarket.repository.UserRepository;
import org.example.wantedmarket.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    /* 제품 주문 */
    @PostMapping
    public OrderCreateDto.Response orderProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody OrderCreateDto.Request request) {
        log.info("제품 주문 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        return orderService.orderProduct(userId, request);
    }

    /* 판매 승인 */
    @PatchMapping("/{orderId}/approval")
    public OrderInfoDto approveProductOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long orderId) {
        log.info("판매 승인 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        return orderService.approveProductOrder(userId, orderId);
    }

}
