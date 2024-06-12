package com.example.wanted_market.controller;

import com.example.wanted_market.dto.ResponseDto;
import com.example.wanted_market.dto.response.UserTransactionResponseDto;
import com.example.wanted_market.exception.CommonException;
import com.example.wanted_market.exception.ErrorCode;
import com.example.wanted_market.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /** 구매 **/
    @PostMapping("/purchase/{productId}")
    public ResponseDto<?> purchase(@PathVariable Long productId, HttpSession session) {
        Long userId = (Long)session.getAttribute("loginUser");
        if(userId == null)
            throw new CommonException(ErrorCode.LOGIN_REQUIRED);

        orderService.purchase(productId, userId);
        return ResponseDto.ok("Purchase success");
    }

    /** 판매 승인 **/
    @PatchMapping("/approval/{orderId}")
    public ResponseDto<?> approval(@PathVariable Long orderId, HttpSession session) {
        Long userId = (Long)session.getAttribute("loginUser");
        if(userId == null)
            throw new CommonException(ErrorCode.LOGIN_REQUIRED);

        orderService.approval(orderId, userId);
        return ResponseDto.ok("Sale Approval success");
    }

    /** 사용자 거래 목록 조회 **/
    @GetMapping("/my-transactions")
    public ResponseDto<?> getMyTransaction(HttpSession session) {
        Long userId = (Long)session.getAttribute("loginUser");
        if(userId == null)
            throw new CommonException(ErrorCode.LOGIN_REQUIRED);

        List<UserTransactionResponseDto> transactions = orderService.getMyTransaction(userId);
        return ResponseDto.ok(transactions);
    }
}
