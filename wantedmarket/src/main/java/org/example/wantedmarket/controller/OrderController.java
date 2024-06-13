package org.example.wantedmarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.common.DataResponseDto;
import org.example.wantedmarket.dto.common.ErrorResponseDto;
import org.example.wantedmarket.dto.common.ResponseDto;
import org.example.wantedmarket.dto.order.OrderCreateDto;
import org.example.wantedmarket.dto.order.OrderInfoDto;
import org.example.wantedmarket.dto.user.CustomUserDetails;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.repository.UserRepository;
import org.example.wantedmarket.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    /* 제품 주문 */

    @PostMapping
    public ResponseDto orderProduct(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody OrderCreateDto.Request request) {
        log.info("제품 주문 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>( orderService.orderProduct(userId, request));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 내 거래내역 보기 */
    @GetMapping("/me")
    public ResponseDto getMyTransactionList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("내 거래내역 보기 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>(orderService.findMyTransactionList(userId));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 판매 승인 */
    @PatchMapping("/{orderId}/approve")
    public ResponseDto approveProductOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long orderId) {
        log.info("판매 승인 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>(orderService.approveProductOrder(userId, orderId));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

    /* 구매 확정 */
    @PatchMapping("/{orderId}/confirm")
    public ResponseDto confirmProductOrder(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long orderId) {
        log.info("구매 확정 api");

        String username = customUserDetails.getUsername();
        Long userId = userRepository.findByUsername(username).getId();

        try {
            return new DataResponseDto<>(orderService.confirmProductOrder(userId, orderId));
        } catch (CustomException exception) {
            return new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
        }
    }

}
