package com.market.wanted.order;

import com.market.wanted.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Long> add(@AuthenticationPrincipal MemberDetails memberDetails,
                                    @RequestBody OrderAddDto orderAddDto){
        Long orderId = orderService.add(orderAddDto, memberDetails.getId());
        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/salesApproval")
    public ResponseEntity salesApproval(@RequestBody OrderSalesApprovalDto orderSalesApprovalDto){
        orderService.salesApproval(orderSalesApprovalDto);
        return ResponseEntity.ok("판매승인을 완료했습니다.");
    }

    @PostMapping("/purchaseConfirmation")
    public ResponseEntity purchaseConfirmation(@RequestBody OrderPurchaseConfirmationDto orderPurchaseConfirmationDto){
        orderService.purchaseConfirmation(orderPurchaseConfirmationDto);
        return ResponseEntity.ok("구매확정을 완료했습니다.");
    }
}
