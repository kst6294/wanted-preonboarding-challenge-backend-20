package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.order.OrderCreateRequest;
import org.example.wantedmarket.dto.order.OrderResponse;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.exception.ErrorCode;
import org.example.wantedmarket.domain.Order;
import org.example.wantedmarket.domain.Product;
import org.example.wantedmarket.domain.User;
import org.example.wantedmarket.repository.jpa.OrderJpaRepository;
import org.example.wantedmarket.repository.ProductRepository;
import org.example.wantedmarket.repository.jpa.UserJpaRepository;
import org.example.wantedmarket.status.OrderStatus;
import org.example.wantedmarket.status.ProductStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpaRepository orderRepository;
    private final UserJpaRepository userRepository;
    private final ProductRepository productRepository;

    /* 제품 주문
    *
    * 추가 판매 가능한 수량이 남아있을 경우 : 제품(판매중 -> 판매중), 주문(대기중)
    * 추가 판매 가능한 수량이 없을 경우 : 제품(판매중 -> 예약중), 주문(대기중)
    */
    @Transactional
    public OrderResponse orderProduct(Long userId, OrderCreateRequest request) {
        User buyer = userRepository.findById(userId).orElseThrow(
                () ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findByWithPessimisticLock(request.getProductId());

        User seller = userRepository.findById(product.getSeller().getId()).orElseThrow(
                () ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        if (orderRepository.existsByBuyerId(buyer.getId())) {
            throw new CustomException(ErrorCode.ALREADY_ORDERED);
        }

        if (product.getStatus() == ProductStatus.IN_RESERVATION) {
            throw new CustomException(ErrorCode.PRODUCT_IN_RESERVATION);
        }

        if (product.getStatus() == ProductStatus.SOLD_OUT) {
            throw new CustomException(ErrorCode.PRODUCT_SOLD_OUT);
        }

        if (product.getSeller().getId().equals(userId)) {
            throw new CustomException(ErrorCode.ORDER_MY_PRODUCT_NOT_ALLOWED);
        }

        // 재고가 주문수량보다 부족할 때
        if (product.getQuantity() < request.getQuantity()) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_ENOUGH);
        }

        // 추가 판매 가능한 수량이 없을 경우
        if (product.getQuantity().equals(request.getQuantity())) {
            product.modifyStatus(ProductStatus.IN_RESERVATION);
        }

        // 주문 수량을 뺀 만큼 제품 재고 수량 변경
        product.modifyQuantity(request.getQuantity());

        Order newOrder = orderRepository.save(
                Order.builder()
                .quantity(request.getQuantity())
                .confirmedPrice(product.getPrice()) // 제품 주문을 한 순간, 구매 가격 확정
                .product(product)
                .buyer(buyer)
                .seller(seller)
                .status(OrderStatus.PENDING)
                .build());

        return OrderResponse.from(newOrder);
    }

    /* 주문 승인
     *
     * 추가 판매 가능한 수량이 남아있을 경우 : 제품(판매중 -> 판매중), 주문(대기중 -> 판매 승인)
     * 추가 판매 가능한 수량이 없을 경우 : 제품(판매중 -> 예약중), 주문(판매 승인)
     */
    @Transactional
    public OrderResponse approveProductOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () ->  new CustomException(ErrorCode.ORDER_NOT_FOUND));

        // 주문을 승인하는 사용자가 판매자가 맞는지 확인
        if (!order.getSeller().getId().equals(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_SELLER);
        }

        // 판매 승인 가능한 상태가 아님
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new CustomException(ErrorCode.ORDER_NOT_PENDING);
        }

        // 판매 승인
        order.modifyStatus(OrderStatus.APPROVED);

        return OrderResponse.from(order);
    }

    /* 구매 확정
     *
     * 추가 판매 가능한 수량이 남아있을 경우 : 제품(판매중 -> 판매중), 주문(판매 승인 -> 구매 확정)
     * 추가 판매 가능한 수량이 없을 경우 : 제품(예약중 -> 판매 완료), 주문(판매 승인 -> 구매 확정)
     */
    @Transactional
    public OrderResponse confirmProductOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        Product product = order.getProduct();

        // 구매 확정을 하는 사용자가 구매자가 맞는지 확인
        if (!order.getBuyer().getId().equals(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_BUYER);
        }

        // 구매 확정할 수 있는 상태가 아님
        if (order.getStatus() != OrderStatus.APPROVED) {
            throw new CustomException(ErrorCode.ORDER_NOT_APPROVED);
        }

        // 추가 판매 가능한 수량이 없을 경우
        if (product.getQuantity().equals(0)) {
            product.modifyStatus(ProductStatus.SOLD_OUT);
        }

        order.modifyStatus(OrderStatus.CONFIRMED); // 구매 확정

        return OrderResponse.from(order);
    }

    /* 내 거래내역 조회 */
    @Transactional(readOnly = true)
    public List<OrderResponse> findMyTransactionList(Long userId) {
        return orderRepository.findAllBySellerIdOrBuyerId(userId, userId)
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

}
