package com.example.wanted_market.controller;

import com.example.wanted_market.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
