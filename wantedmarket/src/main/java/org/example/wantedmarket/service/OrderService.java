package org.example.wantedmarket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wantedmarket.dto.order.OrderCreateDto;
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
                () ->  new RuntimeException("해당 사용자(구매자)가 존재하지 않습니다."));

        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () ->  new RuntimeException("해당 제품이 존재하지 않습니다."));

        if (product.getStatus() == ProductStatus.COMPLETED) {
            throw new RuntimeException("해당 제품은 판매 완료되었습니다.");
        }

        // todo : 본인이 등록한 제품 주문 x

        User seller = userRepository.findById(product.getSeller().getId()).orElseThrow(
                () ->  new RuntimeException("해당 사용자(판매자)가 존재하지 않습니다."));

        // 주문 진행중
        Order newOrder = orderRepository.save(Order.builder()
                .product(product)
                .buyer(buyer)
                .seller(seller)
                .status(OrderStatus.IN_PROGRESS)
                .build());

        // 상품 예약중
        product.modifyStatus(ProductStatus.IN_RESERVATION);

        // todo: 주문 정보 돌려주기
        return OrderCreateDto.Response.from(newOrder);
    }

    @Transactional
    public void approveProductOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () ->  new RuntimeException("해당 주문이 존재하지 않습니다."));

        Product product = order.getProduct();

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("이미 완료된 주문입니다.");
        }

        // todo : 판매자가 아닌 사람 승인 x

        order.modifyStatus(OrderStatus.COMPLETED);
        product.modifyStatus(ProductStatus.COMPLETED);
    }

}
