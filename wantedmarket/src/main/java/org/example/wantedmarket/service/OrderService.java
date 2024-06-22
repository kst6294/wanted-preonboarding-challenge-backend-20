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
import org.example.wantedmarket.repository.OrderRepository;
import org.example.wantedmarket.repository.UserRepository;
import org.example.wantedmarket.repository.impl.OrderRepositoryImpl;
import org.example.wantedmarket.repository.impl.ProductRepositoryImpl;
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

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
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

        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () ->  new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        User seller = userRepository.findById(product.getOwnerId()).orElseThrow(
                () ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        if (orderRepository.existsByBuyerId(buyer.getId())) {
            throw new CustomException(ErrorCode.ALREADY_ORDERED);
        }

        if (checkProductStatus(product, ProductStatus.IN_RESERVATION)) {
            throw new CustomException(ErrorCode.PRODUCT_IN_RESERVATION);
        }

        if (checkProductStatus(product, ProductStatus.SOLD_OUT)) {
            throw new CustomException(ErrorCode.PRODUCT_SOLD_OUT);
        }

        if (product.isProductOwner(userId)) {
            throw new CustomException(ErrorCode.ORDER_MY_PRODUCT_NOT_ALLOWED);
        }

        if (!product.isEnoughStockForOrder(request.getQuantity())) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_ENOUGH);
        }

        // 추가 판매 불가한 경우
        if (!product.isEnoughStockForNextOrder()) {
            product.modifyProductStatus(ProductStatus.IN_RESERVATION);
        }

        // 주문 수량 만큼 재고 차감
        product.decreaseStock(request.getQuantity());

        Order newOrder = Order.builder()
                .quantity(request.getQuantity())
                .productId(request.getProductId())
                .buyerId(userId)
                .sellerId(seller.getId())
                .orderStatus(OrderStatus.PENDING)
                .build();
        newOrder.determineOrderedPrice(product.getPrice()); // 제품 주문을 한 순간, 구매 가격 확정
        orderRepository.save(newOrder);

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

        if (!order.isOrderSeller(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_SELLER);
        }

        if (!checkOrderStatus(order, OrderStatus.PENDING)) {
            throw new CustomException(ErrorCode.ORDER_NOT_PENDING);
        }

        // 판매 승인
        order.modifyOrderStatus(OrderStatus.APPROVED);

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

        Product product = productRepository.findById(order.getProductId()).orElseThrow(
                () -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!order.isOrderBuyer(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_BUYER);
        }

        if (!checkOrderStatus(order, OrderStatus.APPROVED)) {
            throw new CustomException(ErrorCode.ORDER_NOT_APPROVED);
        }

        // 추가 판매 불가
        if (!product.isEnoughStockForNextOrder()) {
            product.modifyProductStatus(ProductStatus.SOLD_OUT);
        }

        // 구매 확정
        order.modifyOrderStatus(OrderStatus.CONFIRMED);

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

    private boolean checkProductStatus(Product product, ProductStatus productStatus) {
        return product.getProductStatus().equals(productStatus);
    }

    private boolean checkOrderStatus(Order order, OrderStatus orderStatus) {
        return order.getOrderStatus().equals(orderStatus);
    }

}
