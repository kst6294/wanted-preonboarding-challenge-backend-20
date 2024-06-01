package com.market.wanted.order;

import com.market.wanted.item.Item;
import com.market.wanted.item.ItemRepository;
import com.market.wanted.member.Member;
import com.market.wanted.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OrderTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    @DisplayName("구매")
    void buy() {
        // given
        Member buyer = Member.builder()
                .username("usernameA")
                .name("nameA")
                .password("passwordA")
                .build();
        memberRepository.save(buyer);

        Member seller = Member.builder()
                .username("usernameB")
                .name("nameB")
                .password("passwordB")
                .build();
        memberRepository.save(seller);

        Item item = Item.builder()
                .name("name")
                .price(1000l)
                .saleCount(10l)
                .sellerId(1l)
                .build();
        itemRepository.save(item);

        // when
        Order order = Order.builder()
                .buyer(buyer)
                .seller(seller)
                .item(item)
                .build();
        orderRepository.save(order);
        item.removeCount();
        item.addReservationCount();

        // then
        Assertions.assertEquals(order.getBuyer().getId(), buyer.getId());
        Assertions.assertEquals(order.getSeller().getId(), seller.getId());
        Assertions.assertEquals(order.getItemId(), item.getId());
        Assertions.assertEquals(order.getStatus(), OrderStatus.SALES_APPROVAL_WAITING);
        Assertions.assertEquals(item.getSaleCount(), 9l);
        Assertions.assertEquals(item.getReservationCount(), 1l);
    }

    @Test
    @Transactional
    @DisplayName("판매승인 하기")
    void salesApproval() {
        // given
        Member buyer = Member.builder()
                .username("usernameA")
                .name("nameA")
                .password("passwordA")
                .build();
        memberRepository.save(buyer);

        Member seller = Member.builder()
                .username("usernameB")
                .name("nameB")
                .password("passwordB")
                .build();
        memberRepository.save(seller);

        Item item = Item.builder()
                .name("name")
                .price(1000l)
                .saleCount(10l)
                .sellerId(1l)
                .build();
        itemRepository.save(item);

        Order order = Order.builder()
                .buyer(buyer)
                .seller(seller)
                .item(item)
                .build();
        orderRepository.save(order);
        // when
        order.salesApproval();

        // then
        Assertions.assertEquals(order.getStatus(), OrderStatus.PURCHASE_CONFIRMATION_WAITING);
    }

    @Test
    @Transactional
    @DisplayName("구매확정 하기")
    void purchaseConfirmation() {
        // given
        Member buyer = Member.builder()
                .username("usernameA")
                .name("nameA")
                .password("passwordA")
                .build();
        memberRepository.save(buyer);

        Member seller = Member.builder()
                .username("usernameB")
                .name("nameB")
                .password("passwordB")
                .build();
        memberRepository.save(seller);

        Item item = Item.builder()
                .name("name")
                .price(1000l)
                .saleCount(10l)
                .sellerId(1l)
                .build();
        itemRepository.save(item);

        Order order = Order.builder()
                .buyer(buyer)
                .seller(seller)
                .item(item)
                .build();
        orderRepository.save(order);
        // when
        order.purchaseConfirmation();

        // then
        Assertions.assertEquals(order.getStatus(), OrderStatus.COMPLETE);
    }
}
