package wanted.preonboarding.backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.preonboarding.backend.auth.Session;
import wanted.preonboarding.backend.dto.request.OrderSaveRequest;
import wanted.preonboarding.backend.dto.request.OrderStatusUpdateRequest;
import wanted.preonboarding.backend.service.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 등록
     */
    @PostMapping
    public void addOrder(@RequestBody final OrderSaveRequest request,
                         final HttpSession session) {
        Long memberId = (Long) session.getAttribute(Session.MEMBER);
        orderService.addOrder(memberId, request);
    }

    /**
     * 판매 승인
     */
    @PutMapping("/approval")
    public void approveOrder(@RequestBody final OrderStatusUpdateRequest request,
                             final HttpSession session) {
        Long sellerId = (Long) session.getAttribute(Session.MEMBER);
        orderService.approveOrder(sellerId, request);
    }

    /**
     * 구매 승인
     */
    @PutMapping("/completion")
    public void completeOrder(@RequestBody final OrderStatusUpdateRequest request,
                              final HttpSession session) {
        Long memberId = (Long) session.getAttribute(Session.MEMBER);
        orderService.completeOrder(memberId, request);
    }
}
