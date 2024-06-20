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
import com.wanted.market.order.model.OrderStatus;
import com.wanted.market.order.repository.OrderRepository;
import com.wanted.market.product.domain.Product;
import com.wanted.market.product.exception.ProductErrorCode;
import com.wanted.market.product.exception.ProductException;
import com.wanted.market.product.model.ProductStatus;
import com.wanted.market.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public OrderResponseDto order(String email, OrderRequestDto orderRequestDto) {

        // 주문자 확인
        Member member = memberRepository.findByEmail(email);
        memberRepository.findById(member.getId()).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND));

        // 주문하려는 상품 확인
        Product product = productRepository.findById(orderRequestDto.getProductId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));

        // 상품 판매중 인지 확인
        if(product.getProductStatus() != ProductStatus.ON_SALE){
            throw new ProductException(ProductErrorCode.STATUS_NOT_ON_SALE);
        }

        Order order = Order.builder()
                .product(product)
                .seller(product.getSeller())
                .buyer(member)
                .orderStatus(OrderStatus.TRADING)
                .build();

        // 상품 수량 차감
        product.order();

        return OrderResponseDto.createFromEntity(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getAll() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(OrderResponseDto::createFromEntity)
                .collect(Collectors.toList());
    }

    /* 판매자 판매 승인 */
    @Override
    public OrderResponseDto approveOrder(String email, Integer orderId){
        // 판매자
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new MemberException(MemberErrorCode.NOT_FOUND);
        }

        // 해당 주문 조회
        Order order = orderRepository.findById(orderId).orElseThrow( () -> new OrderException(OrderErrorCode.NOT_FOUND));

        // 사용자가 seller 아니거나, 해당 상품 판매자가 아닐때
        if(!order.getSeller().getId().equals(member.getId())){
            log.info("approveOrder : 해당 상품 판매자가 아님");
            throw new MemberException(MemberErrorCode.NOT_SELLER);
        }

        // 주문 상태 승인, 제품 상태 판매완료
        order.modifyStatus(OrderStatus.TRADE_APPROVE);
        order.getProduct().modifyStatus(ProductStatus.COMPLETED);

        return OrderResponseDto.createFromEntity(order);
    }
}
