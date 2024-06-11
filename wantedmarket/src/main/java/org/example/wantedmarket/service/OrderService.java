package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.order.OrderCreateDto;
import org.example.wantedmarket.dto.order.OrderInfoDto;
import org.example.wantedmarket.error.CustomException;
import org.example.wantedmarket.error.ErrorCode;
import org.example.wantedmarket.model.Order;
import org.example.wantedmarket.model.Product;
import org.example.wantedmarket.model.User;
import org.example.wantedmarket.repository.OrderRepository;
import org.example.wantedmarket.repository.ProductRepository;
import org.example.wantedmarket.repository.UserRepository;
import org.example.wantedmarket.status.OrderStatus;
import org.example.wantedmarket.status.ProductStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /* 제품 주문 */
    @Transactional
    public OrderCreateDto.Response orderProduct(Long userId, OrderCreateDto.Request request) {
        User buyer = userRepository.findById(userId).orElseThrow(
                () ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () ->  new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        User seller = userRepository.findById(product.getSeller().getId()).orElseThrow(
                () ->  new CustomException(ErrorCode.USER_NOT_FOUND));

        if (product.getStatus() == ProductStatus.COMPLETED) {
            throw new CustomException(ErrorCode.PRODUCT_SOLD_OUT);
        }

        if (product.getSeller().getId() == userId) {
            throw new CustomException(ErrorCode.ORDER_MY_PRODUCT_NOT_ALLOWED);
        }

        // 주문 진행중
        Order newOrder = orderRepository.save(Order.builder()
                .product(product)
                .buyer(buyer)
                .seller(seller)
                .status(OrderStatus.IN_PROGRESS)
                .build());

        // 상품 예약중
        product.modifyStatus(ProductStatus.IN_RESERVATION);

        return OrderCreateDto.Response.from(newOrder);
    }

    @Transactional
    public OrderInfoDto approveProductOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () ->  new CustomException(ErrorCode.ORDER_NOT_FOUND));

        Product product = order.getProduct();

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new CustomException(ErrorCode.ORDER_COMPLETED);
        }

        if (order.getSeller().getId() != userId) {
            throw new CustomException(ErrorCode.USER_NOT_SELLER);
        }

        // 주문과 제품 상태 변경
        order.modifyStatus(OrderStatus.COMPLETED);
        product.modifyStatus(ProductStatus.COMPLETED);

        return new OrderInfoDto(orderId, order.getProduct().getId(), order.getSeller().getId(), order.getBuyer().getId(), order.getStatus());
    }

}
