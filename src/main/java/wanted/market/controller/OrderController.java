package wanted.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wanted.market.entity.Order;
import wanted.market.service.OrderService;

import java.util.HashMap;
import java.util.List;
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
            return ResponseEntity.ok("주문예약을 완료하였습니다.");
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/my")
    public ResponseEntity getMyOrders() {
        try {
            List<Order> orders = orderService.getMyOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errors);
        }
    }

    // 판매승인
    @PostMapping("/confirm")
    public ResponseEntity confirmOrder(@RequestBody Map<String, String> request) {
        try {
            Long orderId = Long.parseLong(request.get("orderId").toString());

            orderService.confirmOrder(orderId);
            return ResponseEntity.ok("판매승인을 완료하였습니다.");
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
