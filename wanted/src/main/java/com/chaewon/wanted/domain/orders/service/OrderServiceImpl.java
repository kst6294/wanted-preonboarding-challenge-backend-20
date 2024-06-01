package com.chaewon.wanted.domain.orders.service;

import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.member.exception.MemberNotFoundException;
import com.chaewon.wanted.domain.member.repository.MemberRepository;
import com.chaewon.wanted.domain.orders.dto.OrderRequestDto;
import com.chaewon.wanted.domain.orders.entity.Orders;
import com.chaewon.wanted.domain.orders.exception.SelfPurchaseException;
import com.chaewon.wanted.domain.orders.repository.OrderRepository;
import com.chaewon.wanted.domain.product.entity.Product;
import com.chaewon.wanted.domain.product.entity.ProductStatus;
import com.chaewon.wanted.domain.product.exception.ProductNotFoundException;
import com.chaewon.wanted.domain.product.exception.ProductUnavailableException;
import com.chaewon.wanted.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createOrder(String email, OrderRequestDto orderRequestDto) {
        Member member = getByEmail(email);
        Product product = getByProduct(Long.valueOf(String.valueOf(orderRequestDto.getProductId())));

        validateOrderRequest(member, product);

        product.updateProductStatus(ProductStatus.예약중);
        Orders order = OrderRequestDto.from(orderRequestDto, member, product);

        orderRepository.save(order);
    }

    private void validateOrderRequest(Member member, Product product) {
        if (product.getMember().equals(member)) {
            throw new SelfPurchaseException("판매자는 자신의 제품을 구매할 수 없습니다.");
        }

        if (product.getProductStatus() != ProductStatus.판매중) {
            throw new ProductUnavailableException("현재 다른 사용자와 거래 중이거나 이미 완료된 제품입니다.");
        }
    }

    private Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다."));
    }

    private Product getByProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("해당 제품을 찾을 수 없습니다. 제툼 ID: " + productId));
    }
}
