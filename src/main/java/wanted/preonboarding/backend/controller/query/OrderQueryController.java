package wanted.preonboarding.backend.controller.query;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.preonboarding.backend.auth.Session;
import wanted.preonboarding.backend.dto.response.OrderListResponse;
import wanted.preonboarding.backend.service.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderQueryController {

    private final OrderService orderService;

    /**
     * 특정 아이템에 대한 판매자와 사용자(구매자) 간 거래 내역 조회
     */
    @GetMapping("/history/{itemId}")
    public OrderListResponse getOrderHistory(@PathVariable final Long itemId,
                                final HttpSession session) {
        Long memberId = (Long) session.getAttribute(Session.MEMBER);
        return orderService.getOrderHistory(memberId, itemId);
    }

    /**
     * 사용자가 구매한 제품 목록 조회
     */
    @GetMapping("/completion")
    public OrderListResponse getCompleteOrders(final HttpSession session) {
        Long memberId = (Long) session.getAttribute(Session.MEMBER);
        return orderService.getCompleteOrders(memberId);
    }

    /**
     * 사용자가 구매 요청한 제품 중 판매 승인된 제품 목록 조회
     */
    @GetMapping("/approval")
    public OrderListResponse getApprovedOrders(final HttpSession session) {
        Long memberId = (Long) session.getAttribute(Session.MEMBER);
        return orderService.getApprovedOrders(memberId);
    }

    /**
     * 사용자가 예약 중인 제품 목록 조회
     */
    @GetMapping("/reservation")
    public OrderListResponse getReservedOrders(final HttpSession session) {
        Long memberId = (Long) session.getAttribute(Session.MEMBER);
        return orderService.getReservedOrders(memberId);
    }


    /**
     * 사용자가 예약 요청 받은 제품 목록 조회
     */
    @GetMapping("/seller/reservation")
    public OrderListResponse getRequestedReservedOrders(final HttpSession session) {
        Long sellerId = (Long) session.getAttribute(Session.MEMBER);
        return orderService.getRequestedReservedOrders(sellerId);
    }
}
