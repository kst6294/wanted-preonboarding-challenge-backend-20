package com.market.wanted;

import com.market.wanted.item.Item;
import com.market.wanted.item.ItemDetailResponse;
import com.market.wanted.item.ItemRepository;
import com.market.wanted.item.ItemResponse;
import com.market.wanted.order.Order;
import com.market.wanted.order.OrderRepository;
import com.market.wanted.order.OrderResponse;
import com.market.wanted.order.OrderStatus;
import com.market.wanted.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/")
    public String init(@AuthenticationPrincipal MemberDetails memberDetails,
                       @RequestParam(value = "error", required = false) String error,
                       Model model){
        model.addAttribute("member", memberDetails);
        model.addAttribute("error", error);
        return "index";
    }

    @GetMapping("/signup")
    public String createSignUpView(){ return "signUp"; }

    @GetMapping("/signupSuccess")
    public String createSignUpSuccessView(){
        return "signUpSuccess";
    }

    @GetMapping("/addItem")
    public String createAddItemView(){
        return "addItem";
    }

    @GetMapping("/addItemSuccess")
    public String createAddItemSuccessView(){
        return "addItemSuccess";
    }

    @GetMapping("/items")
    public String createItemListView(Model model){
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", new ItemResponse(result));
        return "itemList";
    }

    @GetMapping("/item/{id}")
    public String createItemView(@AuthenticationPrincipal MemberDetails memberDetails,
                                 @PathVariable(value = "id")Long itemId,
                                 Model model){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("제품이 존재하지 않습니다."));
        if(memberDetails != null){
            List<Order> buys = orderRepository.findBuyHistory(memberDetails.getId(), itemId);
            model.addAttribute("buys", new OrderResponse(buys));
            model.addAttribute("seller", false);
            if(memberDetails.getId() == item.getSellerId()){
                List<Order> sells = orderRepository.findSellHistory(memberDetails.getId(), itemId);
                model.addAttribute("sells", new OrderResponse(sells));
                model.addAttribute("seller", true);
            }
        }
        model.addAttribute("item", new ItemDetailResponse(item));
        return "item";
    }

    @GetMapping("/buyItemSuccess")
    public String createBuyItemSuccessView(){
        return "buyItemSuccess";
    }

    @GetMapping("/buyItems")
    public String createBuyItemListView(@AuthenticationPrincipal MemberDetails memberDetails,
                                        Model model){
        List<Order> result = orderRepository.findBuyItem(memberDetails.getId(), OrderStatus.COMPLETE);
        model.addAttribute("items", new OrderResponse(result));
        return "buyItemList";
    }

    @GetMapping("/reservationItems")
    public String createReservationItemListView(@AuthenticationPrincipal MemberDetails memberDetails,
                                                Model model){
        List<Order> buys = orderRepository.findReservationBuyItem(memberDetails.getId(), OrderStatus.COMPLETE);
        List<Order> sells = orderRepository.findReservationSellItem(memberDetails.getId(), OrderStatus.COMPLETE);
        model.addAttribute("buys", new OrderResponse(buys));
        model.addAttribute("sells", new OrderResponse(sells));
        return "reservationItemList";
    }

    @GetMapping("/salesApprovalSuccess")
    public String createSalesApprovalSuccessView(){
        return "salesApprovalSuccess";
    }

    @GetMapping("/purchaseConfirmationSuccess")
    public String createPurchaseConfirmationSuccessView(){
        return "purchaseConfirmationSuccess";
    }
}
