package wanted.challenge.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.challenge.aop.api.ApiResponse;
import wanted.challenge.mypage.mapper.MyPageMapper;
import wanted.challenge.order.service.OrderService;

import java.util.List;

import static wanted.challenge.mypage.dto.response.MyPageResponseDto.*;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final OrderService orderService;
    private final MyPageMapper mapper;


    @GetMapping("/seller")
    public ApiResponse<List<orderListItemInfo>> getSellerInfo(
            @RequestHeader("memberId") Long memberId) {
        return ApiResponse.success(mapper.orderToOrderListItemInfo(orderService.getSellOrderList(memberId)));

    }

    @GetMapping("/seller/{order_id}")
    public ApiResponse<sellOrderDetail> getSellerOrderDetail(
            @RequestHeader("memberId") Long memberId,
            @PathVariable("order_id") Long orderId) throws Exception {

        return ApiResponse.success(orderService.getSellerOrderDetail(memberId, orderId));
    }

    @GetMapping("/buyer")
    public ApiResponse<List<orderListItemInfo>> getBuyerInfo(
            @RequestHeader("memberId") Long memberId) {
        return ApiResponse.success(mapper.orderToOrderListItemInfo(orderService.getBuyOrderList(memberId)));
//        return null;
    }

    @GetMapping("/buyer/{order_id}")
    public ApiResponse<buyOrderDetail> getBuyerOrderDetail(
            @RequestHeader("memberId") Long memberId,
            @PathVariable("order_id") Long orderId) throws Exception {
        return ApiResponse.success(orderService.getBuyerOrderDetail(memberId, orderId));
    }

    @PostMapping("/seller/{order_id}/confirm")
    public ApiResponse<String> confirmOrder(
            @RequestHeader("memberId") Long memberId,
            @PathVariable("order_id") Long orderId) {
        // 판매승인
        orderService.sellConfirm(memberId, orderId);
        return ApiResponse.success();
    }

    @PostMapping("/buyer/{order_id}/finish")
    public ApiResponse<String> finishOrder(
            @RequestHeader("memberId") Long memberId,
            @PathVariable("order_id") Long orderId) {
        orderService.buyFinish(memberId, orderId);
        return ApiResponse.success();
    }


}
