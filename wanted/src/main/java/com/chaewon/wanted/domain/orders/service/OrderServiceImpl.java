package com.chaewon.wanted.domain.orders.service;

import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.member.exception.MemberNotFoundException;
import com.chaewon.wanted.domain.member.repository.MemberRepository;
import com.chaewon.wanted.domain.orders.dto.request.OrderRequestDto;
import com.chaewon.wanted.domain.orders.dto.response.BuyerPurchaseHistoryResponseDto;
import com.chaewon.wanted.domain.orders.entity.OrderStatus;
import com.chaewon.wanted.domain.orders.entity.Orders;
import com.chaewon.wanted.domain.orders.exception.*;
import com.chaewon.wanted.domain.orders.repository.OrderRepository;
import com.chaewon.wanted.domain.orders.dto.response.SalesApprovalListResponseDto;
import com.chaewon.wanted.domain.product.entity.Product;
import com.chaewon.wanted.domain.product.exception.NoProductsForSaleException;
import com.chaewon.wanted.domain.product.exception.ProductNotFoundException;
import com.chaewon.wanted.domain.product.exception.ProductUnavailableException;
import com.chaewon.wanted.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Member buyer = getByEmail(email);
        Product product = getByProduct(Long.valueOf(String.valueOf(orderRequestDto.getProductId())));
        Member seller = product.getMember();

        validateOrderRequest(buyer, product);

        Orders order = OrderRequestDto.from(orderRequestDto, buyer, seller, product);
        orderRepository.save(order);

        long orderCount = orderRepository.countByProductAndOrderStatusNot(product, OrderStatus.거래확정);
        product.updateProductStatusOnOrderCreation(orderCount);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BuyerPurchaseHistoryResponseDto> getMyPurchasesConditional(String email, String orderStatus, Pageable pageable) {
        Member member = getByEmail(email);
        Page<Orders> ordersPage = findOrdersByStatus(member.getEmail(), orderStatus, pageable);

        if (ordersPage.isEmpty()) {
            throw new NoOrderException("거래 중인 제품이 없습니다.");
        }

        return ordersPage.map(BuyerPurchaseHistoryResponseDto::from);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesApprovalListResponseDto> findMySales(String email, Pageable pageable) {
        Page<Orders> orders = orderRepository.findBySellerEmailAndOrderStatus(email, OrderStatus.거래시작, pageable);

        if (orders.isEmpty()) {
            throw new NoProductsForSaleException("거래 중인 제품이 없습니다.");
        }

        return orders.map(order -> SalesApprovalListResponseDto.from(order, order.getSeller()));
    }

    @Override
    @Transactional
    public void approveOrder(String email, Long orderId) {
        Orders order = getByOrder(orderId);

        if (!order.getSeller().getEmail().equals(email)) {
            throw new UnauthorizedException("판매자만 거래를 승인 할 수 있습니다.");
        }

        if (order.getOrderStatus() == OrderStatus.판매승인) {
            throw new DuplicateApprovalException("이미 판매 승인이 된 제품입니다.");
        }

        order.updateOrderStatus(OrderStatus.판매승인);
    }

    @Override
    @Transactional
    public void confirmPurchase(String email, Long orderId) {
        Orders order = getByOrder(orderId);

        if (!order.getBuyer().getEmail().equals(email)) {
            throw new OrderAccessException("이 주문에 대한 접근 권한이 없습니다.");
        }

        if (!order.getOrderStatus().equals(OrderStatus.판매승인)) {
            if (order.getOrderStatus().equals(OrderStatus.거래확정)) {
                throw new OrderAlreadyConfirmedException("이미 거래 확정된 제품입니다.");
            }
            throw new OrderStatusNotApprovedException("주문 상태가 판매승인이 아닙니다.");
        }

        Product product = order.getProduct();
        product.decreaseQuantity(1);

        order.updateOrderStatus(OrderStatus.거래확정);
        updateProductStatus(product);
    }

    private void updateProductStatus(Product product) {
        // 모든 주문이 거래확정 상태인지 확인
        boolean allOrdersConfirmed = orderRepository.countByProductAndOrderStatus(product, OrderStatus.거래확정) == orderRepository.countByProduct(product);
        // 제품의 남은 수량 확인
        boolean isQuantityAvailable = product.getQuantity() > 0;

        // 대기중인 주문이 있거나, 남은 수량이 있을 경우
        boolean hasPending = orderRepository.existsByProductAndOrderStatus(product, OrderStatus.판매승인) || orderRepository.existsByProductAndOrderStatus(product, OrderStatus.거래확정);
        product.updateProductStatusOnOrders(allOrdersConfirmed, isQuantityAvailable, hasPending);
    }

    private void validateOrderRequest(Member buyer, Product product) {
        if (product.getMember().equals(buyer)) {
            throw new SelfPurchaseException("판매자는 자신의 제품을 구매할 수 없습니다.");
        }

        // 현재 제품에 대한 거래 요청 수
        long pendingOrderCount = orderRepository.countByProductAndOrderStatusNot(product, OrderStatus.거래확정);
        // 제품의 총 수량과 거래 요청 수를 비교
        if (product.getQuantity() - pendingOrderCount <= 0) {
            throw new ProductUnavailableException("현재 구매 가능한 제품 수량이 없거나 모두 거래 진행 중입니다.");
        }

        if (orderRepository.existsByBuyerAndProduct(buyer, product)) {
            throw new DuplicatePurchaseException("이미 구매한 제품은 중복 구매할 수 없습니다.");
        }
    }

    private Page<Orders> findOrdersByStatus(String email, String orderStatus, Pageable pageable) {
        if (orderStatus == null) {
            return orderRepository.findByBuyerEmail(email, pageable);
        } else {
            OrderStatus status = OrderStatus.valueOf(orderStatus.toUpperCase());
            return orderRepository.findByBuyerEmailAndOrderStatus(email, status, pageable);
        }
    }

    private Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("해당 멤버를 찾을 수 없습니다."));
    }

    private Product getByProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("해당 제품을 찾을 수 없습니다. 제품 ID: " + productId));
    }

    public Orders getByOrder(Long orderId) {
        return orderRepository.findByIdWithSellerAndProduct(orderId)
                .orElseThrow(() -> new OrderNotFoundException("해당 주문을 찾을 수 없습니다."));
    }

}

