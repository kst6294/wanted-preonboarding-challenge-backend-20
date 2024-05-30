package wanted.market.api.domain.orders.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.api.domain.orders.dto.request.ApproveOrderRequestDto;
import wanted.market.api.domain.orders.dto.request.RegisterOrderRequestDto;
import wanted.market.api.domain.orders.dto.response.OrderListResponseDto;
import wanted.market.api.domain.orders.dto.response.RegisterOrderResponseDto;
import wanted.market.api.domain.orders.dto.response.ApproveOrderResponseDto;
import wanted.market.api.domain.orders.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<RegisterOrderResponseDto> orderProduct(HttpServletRequest request, @RequestBody RegisterOrderRequestDto requestDto){
        return ResponseEntity.ok().body(orderService.orderProduct(request, requestDto));
    }

    @GetMapping("/selling")
    public ResponseEntity<OrderListResponseDto> searchSellOrderList(HttpServletRequest request, @RequestParam(required = false, value = "target") Long targetUser){

        return ResponseEntity.ok().body(orderService.searchSellOrderList(request, targetUser));
    }
    @GetMapping("/buying")
    public ResponseEntity<OrderListResponseDto> searchBuyOrderList(HttpServletRequest request, @RequestParam(required = false, value = "target") Long targetUser){

        return ResponseEntity.ok().body(orderService.searchBuyOrderList(request, targetUser));
    }
    @PutMapping("/approving")
    public ResponseEntity<ApproveOrderResponseDto> approveOrder(HttpServletRequest request, @RequestBody ApproveOrderRequestDto requestDto){
        return ResponseEntity.ok().body(orderService.approveOrder(request, requestDto));
    }
}
