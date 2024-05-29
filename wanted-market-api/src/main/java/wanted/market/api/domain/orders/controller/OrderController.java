package wanted.market.api.domain.orders.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.api.domain.orders.dto.request.RegisterOrderRequestDto;
import wanted.market.api.domain.orders.dto.response.RegisterOrderResponseDto;
import wanted.market.api.domain.orders.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<RegisterOrderResponseDto> orderProduct(@RequestBody RegisterOrderRequestDto requestDto){
        return ResponseEntity.ok().body(orderService.orderProduct(requestDto));
    }
}
