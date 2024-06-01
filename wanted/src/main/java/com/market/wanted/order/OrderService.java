package com.market.wanted.order;

import com.market.wanted.item.Item;
import com.market.wanted.item.ItemRepository;
import com.market.wanted.member.Member;
import com.market.wanted.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long add(OrderAddDto orderAddDto, Long buyerId){
        Item item = itemRepository.findById(orderAddDto.getItemId()).orElseThrow(() -> new IllegalArgumentException("존재하는 제품이 없습니다."));
        item.removeCount();
        item.addReservationCount();

        Member buyer = memberRepository.findById(buyerId).orElseThrow(() -> new IllegalArgumentException("존재하는 회원이 없습니다."));
        Member seller = memberRepository.findById(item.getSellerId()).orElseThrow(() -> new IllegalArgumentException("존재하는 회원이 없습니다."));
        Order order = Order.builder()
                .item(item)
                .buyer(buyer)
                .seller(seller)
                .build();
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void salesApproval(OrderSalesApprovalDto orderSalesApprovalDto){
        Order order = orderRepository.findById(orderSalesApprovalDto.getOrderId()).orElseThrow(() -> new IllegalArgumentException("존재하는 제품이 없습니다."));
        order.salesApproval();
    }

    @Transactional
    public void purchaseConfirmation(OrderPurchaseConfirmationDto orderPurchaseConfirmationDto){
        Order order = orderRepository.findById(orderPurchaseConfirmationDto.getOrderId()).orElseThrow(() -> new IllegalArgumentException("존재하는 제품이 없습니다."));
        Item item = itemRepository.findById(order.getItemId()).orElseThrow(() -> new IllegalArgumentException("존재하는 제품이 없습니다."));
        item.removeReservationCount();
        order.purchaseConfirmation();
    }
}
