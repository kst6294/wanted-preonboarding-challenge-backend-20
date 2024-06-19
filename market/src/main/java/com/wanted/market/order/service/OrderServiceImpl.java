package com.wanted.market.order.service;

import com.wanted.market.member.domain.Member;
import com.wanted.market.member.exception.MemberErrorCode;
import com.wanted.market.member.exception.MemberException;
import com.wanted.market.member.repository.MemberRepository;
import com.wanted.market.order.domain.Order;
import com.wanted.market.order.dto.OrderRequestDto;
import com.wanted.market.order.dto.OrderResponseDto;
import com.wanted.market.order.exception.OrderErrorCode;
import com.wanted.market.order.exception.OrderException;
import com.wanted.market.order.repository.OrderRepository;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.exception.ProductErrorCode;
import com.wanted.market.product.exception.ProductException;
import com.wanted.market.product.model.ProductStatus;
import com.wanted.market.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public OrderResponseDto order(@Valid @RequestBody OrderRequestDto orderRequestDto) {

        // 주문자 확인
        Member buyer = memberRepository.findById(orderRequestDto.getMemberId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        // 주문하려는 상품 확인
        Product product = productRepository.findById(orderRequestDto.getProductId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));

        // 상품 판매중 인지 확인
        if(product.getStatus() != ProductStatus.ON_SALE){
            throw new ProductException(ProductErrorCode.STATUS_NOT_ON_SALE);
        }

        Order order = Order.builder()
                .product(product)
                .member(buyer)
                .build();

        // 상품 수량 차감
        product.order();

        return OrderResponseDto.createFromEntity(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getAll() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(OrderResponseDto::createFromEntity).collect(Collectors.toList());
    }
}
