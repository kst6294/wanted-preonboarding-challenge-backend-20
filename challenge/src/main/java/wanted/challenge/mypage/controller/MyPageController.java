package wanted.challenge.mypage.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wanted.challenge.mypage.dto.response.MyPageResponseDto;
import wanted.challenge.mypage.mapper.MyPageMapper;
import wanted.challenge.mypage.service.MyPageService;

import java.util.List;

import static wanted.challenge.mypage.dto.response.MyPageResponseDto.*;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final MyPageMapper mapper;


    @GetMapping("/seller")
    public List<orderListItemInfo> getSellerInfo(@RequestAttribute("memberId") Long memberId) {
        return mapper.orderToOrderListItemInfo(myPageService.getSellOrderList(memberId));

    }

    @GetMapping("/seller/{order_id}")
    public sellOrderDetail getSellerOrderDetail(@RequestAttribute("memberId") Long memberId, @PathVariable("order_id") Long orderId) throws Exception {

        return myPageService.getSellerOrderDetail(memberId, orderId);
    }

    @GetMapping("/buyer")
    public List<orderListItemInfo> getBuyerInfo(@RequestAttribute("memberId") Long memberId) {
        return mapper.orderToOrderListItemInfo(myPageService.getBuyOrderList(memberId));
//        return null;
    }

    @GetMapping("/buyer/{order_id}")
    public buyOrderDetail getBuyerOrderDetail(@RequestAttribute("memberId") Long memberId, @PathVariable("order_id") Long orderId) throws Exception {
        return myPageService.getBuyerOrderDetail(memberId, orderId);
    }

    @PostMapping("/seller/{order_id}/confirm")
    public String confirmOrder(@RequestAttribute("memberId") Long memberId, @PathVariable("order_id") Long orderId) {
        myPageService.sellConfirm();
        return "confirm";
    }

    @PostMapping("/buyer/{order_id}/finish")
    public String finishOrder(@RequestAttribute("memberId") Long memberId, @PathVariable("order_id") Long orderId) {
        myPageService.buyFinish();
        return "finish";
    }


}
