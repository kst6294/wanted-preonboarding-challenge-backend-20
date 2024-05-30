package wanted.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.service.OrderService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 구매예약
    @PostMapping("/purchase")
    public ResponseEntity registerOrder(@RequestBody Map<String, String> request) {
        try {
            Long productId = Long.parseLong(request.get("productId").toString());

            orderService.registerOrder(productId);
            return ResponseEntity.ok("주문 예약을 완료하였습니다.");
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    // 판매승인
    @PostMapping("/confirm")
    public ResponseEntity confirmOrder(@RequestBody Map<String, String> request) {
        try {
            Long orderId = Long.parseLong(request.get("orderId").toString());

            orderService.confirmOrder(orderId);
            return ResponseEntity.ok("주문을 판매 완료하였습니다.");
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
