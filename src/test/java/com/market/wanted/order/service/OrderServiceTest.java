package com.market.wanted.order.service;

import com.market.wanted.member.entity.Member;
import com.market.wanted.member.repository.MemberRepository;
import com.market.wanted.order.dto.OrderDto;
import com.market.wanted.order.entity.OrderStatus;
import com.market.wanted.product.entity.Product;
import com.market.wanted.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class OrderServiceTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderService orderService;

    @Test
    void createOrder() {
        //given
        Member seller = new Member("1234", "seller", "seller@test.com");
        Member buyer = new Member("1234", "buyer", "buyer@test.com");
        memberRepository.save(seller);
        memberRepository.save(buyer);
        Product product = new Product("itemA", 1000, buyer);
        productRepository.save(product);
        //when
        OrderDto order = orderService.createOrder(seller.getId(), product.getId());

        //then
        assertThat(order.getBuyerId()).isEqualTo(buyer.getId());
        assertThat(order.getSellerId()).isEqualTo(seller.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.RESERVATION);
    }

}