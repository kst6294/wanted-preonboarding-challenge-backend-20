package com.wanted.preonboarding.backend20.domain.order.api;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.order.application.OrderService;
import com.wanted.preonboarding.backend20.domain.order.dto.OrderInfoDto;
import com.wanted.preonboarding.backend20.global.auth.AuthMember;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<String> orderProduct(@RequestParam Long productId, @AuthMember Member buyer) {
        if (buyer == null) throw new CustomException(ErrorCode.AUTHENTICATION_ERROR);
        orderService.orderProduct(productId, buyer);
        return ResponseEntity.ok().body("주문 완료");
    }

    @GetMapping("/history")
    public ResponseEntity<Page<OrderInfoDto>> findOrderHistoryBetweenSellerAndBuyer(@RequestParam Long sellerId, @AuthMember Member buyer, Pageable pageable) {
        if (buyer == null) throw new CustomException(ErrorCode.AUTHENTICATION_ERROR);
        return ResponseEntity.ok(orderService.findOrderHistoryBetweenSellerAndBuyer(sellerId, buyer, pageable));
    }

    @PutMapping("/seller/approve")
    public ResponseEntity<String> approveSellersOrder(@RequestParam Long orderId, @AuthMember Member seller) {
        if (seller == null) throw new CustomException(ErrorCode.AUTHENTICATION_ERROR);
        orderService.approveSellersOrder(orderId, seller);
        return ResponseEntity.ok().body("완료");
    }

    @PutMapping("/buyer/confirm")
    public ResponseEntity<String> confirmBuyersOrder(@RequestParam Long orderId, @AuthMember Member buyer) {
        if (buyer == null) throw new CustomException(ErrorCode.AUTHENTICATION_ERROR);
        orderService.confirmBuyersOrder(orderId, buyer);
        return ResponseEntity.ok().body("완료");
    }
}
