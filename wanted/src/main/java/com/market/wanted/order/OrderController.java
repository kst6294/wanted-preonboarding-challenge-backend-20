package com.market.wanted.order;

import com.market.wanted.item.Item;
import com.market.wanted.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public Long add(@AuthenticationPrincipal MemberDetails memberDetails,
                    @RequestBody OrderAddDto orderAddDto){
        return orderService.add(orderAddDto, memberDetails.getId());
    }

    @PostMapping("/salesApproval")
    public void salesApproval(@AuthenticationPrincipal MemberDetails memberDetails,
                              @RequestBody OrderSalesApprovalDto orderSalesApprovalDto){
        orderService.salesApproval(orderSalesApprovalDto);
    }

    @PostMapping("/purchaseConfirmation")
    public void purchaseConfirmation(@AuthenticationPrincipal MemberDetails memberDetails,
                                     @RequestBody OrderPurchaseConfirmationDto orderPurchaseConfirmationDto){
        orderService.purchaseConfirmation(orderPurchaseConfirmationDto);
    }
}
