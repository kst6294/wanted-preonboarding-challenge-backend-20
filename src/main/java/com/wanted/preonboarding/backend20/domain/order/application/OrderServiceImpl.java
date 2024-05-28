package com.wanted.preonboarding.backend20.domain.order.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.member.repository.MemberRepository;
import com.wanted.preonboarding.backend20.domain.order.domain.Order;
import com.wanted.preonboarding.backend20.domain.order.dto.OrderInfoDto;
import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import com.wanted.preonboarding.backend20.domain.order.repository.OrderRepository;
import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.domain.product.repository.ProductRepository;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import com.wanted.preonboarding.backend20.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void orderProduct(Long id, Member buyer) {
        Product product = findProduct(id);
        product.productReserved();
        Order order = Order.builder()
                .seller(product.getSeller())
                .buyer(buyer)
                .product(product)
                .build();
        orderRepository.save(order);
    }

    @Override
    public Page<OrderInfoDto> findOrderHistoryBetweenSellerAndBuyer(Long sellerId, Member buyer, Pageable pageable) {
        Member seller = memberRepository.findById(sellerId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "판매자를 찾을 수 없습니다."));
        Page<Order> sellerAndBuyerHistory = orderRepository.findBySellerAndBuyer(seller, buyer, pageable);

        return sellerAndBuyerHistory.map(history -> OrderInfoDto.builder()
                .order(history)
                .buyer(buyer)
                .seller(seller)
                .build());
    }

    @Override
    @Transactional
    public void approveSellersOrder(Long orderId, Member seller) {
        Order order = findOrder(orderId);
        if (!order.getSeller().getId().equals(seller.getId())) {
            throw new CustomException(ErrorCode.PERMISSION_DENIED, "해당 제품의 판매자가 아니라 불가능한 작업입니다.");
        }

        order.sellerApprovedForOrder();
    }

    @Override
    @Transactional
    public void confirmBuyersOrder(Long orderId, Member buyer) {
        Order order = findOrder(orderId);
        if (!order.getBuyer().getId().equals(buyer.getId())) {
            throw new CustomException(ErrorCode.PERMISSION_DENIED, "해당 제품의 구매자가 아니라 불가능한 작업입니다.");
        }

        checkSellerOrderStatusIsApproval(order);
        order.buyerConfirmsOrder();
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    private Order findOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "해당 주문을 찾을 수 없습니다."));
    }

    private void checkSellerOrderStatusIsApproval(Order order) {
        if (!order.getSellerOrderStatus().equals(OrderStatus.SELLER_APPROVAL)) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "판매자가 판매승인한 제품만 구매확정 진행이 가능합니다.");
        }
    }
}
