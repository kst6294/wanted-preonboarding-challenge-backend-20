package com.example.wanted_market.controller;

import com.example.wanted_market.dto.response.UserTransactionResponseDto;
import com.example.wanted_market.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.UserTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /** 구매 **/
    @PostMapping("/purchase/{productId}")
    public ResponseEntity<?> purchase(@PathVariable Long productId, HttpSession session) {
        try{
            Long userId = (Long)session.getAttribute("loginUser");
            if(userId == null)
                throw new IllegalArgumentException("로그인이 필요합니다.");

            orderService.purchase(productId, userId);
            return ResponseEntity.ok("Purchase success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Purchase failed: "+e.getMessage());
        }
    }

    /** 판매 승인 **/
    @PatchMapping("/approval/{productId}/{orderId}")
    public ResponseEntity<?> approval(@PathVariable Long productId,
                                      @PathVariable Long orderId, HttpSession session) {
        try{
            Long userId = (Long)session.getAttribute("loginUser");
            if(userId == null)
                throw new IllegalArgumentException("로그인이 필요합니다.");

            orderService.approval(productId, orderId, userId);
            return ResponseEntity.ok("Sale Approval success");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Sale Approval failed: "+e.getMessage());
        }
    }

    /** 사용자 거래 목록 조회 **/
    @GetMapping("/my-transactions")
    public ResponseEntity<?> getMyTransaction(HttpSession session) {
        try{
            Long userId = (Long)session.getAttribute("loginUser");
            if(userId == null)
                throw new IllegalArgumentException("로그인이 필요합니다.");

            List<UserTransactionResponseDto> transactions = orderService.getMyTransaction(userId);
            return ResponseEntity.ok().body(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Get My Transactions failed: "+e.getMessage());
        }
    }
}
