package org.example.preonboarding.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.member.util.MemberUtil;
import org.example.preonboarding.order.exception.NotFoundOrderException;
import org.example.preonboarding.order.model.domain.Order;
import org.example.preonboarding.order.model.payload.request.OrderCreateRequest;
import org.example.preonboarding.order.model.payload.request.OrderSearchRequest;
import org.example.preonboarding.order.model.payload.response.OrderResponse;
import org.example.preonboarding.order.repository.OrderRepository;
import org.example.preonboarding.product.model.entity.Product;
import org.example.preonboarding.product.repository.ProductRepository;
import org.example.preonboarding.stock.entity.Stock;
import org.example.preonboarding.stock.exception.OutOfStockException;
import org.example.preonboarding.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.example.preonboarding.order.model.enums.OrderStatus.APPROVED;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final MemberUtil memberUtil;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime orderedAt) {
        String productNumbers = request.getProductNumber();
        Product product = productRepository.findByProductNumber(productNumbers);

        Stock stock = stockRepository.findByProductNumber(product.getProductNumber());

        if (!stock.isPossibleOrderQuantity()) {
            throw new OutOfStockException("해당 제품에 주문 가능한 재고가 없습니다.");
        }

        // 주문을 넣는 순간 재고 차감
        deductStockQuantities(product);
        updateProductSellingStatus(stock);

        Member currentUser = memberUtil.getCurrentUser();
        Order order = Order.create(product, currentUser, orderedAt);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    @Override
    @Transactional
    public OrderResponse approveOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new NotFoundOrderException("주문을 찾지 못했습니다.")
        );

        if (!Objects.equals(order.getSeller().getId(), memberUtil.getCurrentUser().getId())) {
            throw new IllegalArgumentException("주문의 판매자와 현재 사용자가 일치하지 않습니다.");
        }

        order.approveOrder();
        Order approvedOrder = orderRepository.save(order);

        return OrderResponse.of(approvedOrder);
    }

    @Override
    public List<OrderResponse> searchOrder(OrderSearchRequest orderSearchRequest) {
        List<Order> orders = getOrders(orderSearchRequest);

        return orders.stream().map(OrderResponse::of).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderResponse completeOrder(long orderId) {
        Order order = orderRepository.findByIdAndOrderStatus(orderId, APPROVED).orElseThrow(() ->
                new NotFoundOrderException("판매가 예약중인 주문을 찾지 못했습니다.")
        );

        order.completeOrder();
        Order savedOrder = orderRepository.save(order); // 명시적 save

        Product product = savedOrder.getProduct();

        if (isCompleteAllOrder(product)) {
            product.soldOut();
        }

        Order completedOrder = orderRepository.findById(orderId).orElseThrow(() ->
                new NotFoundOrderException("주문을 찾지 못하였습니다.")
        );

        return OrderResponse.of(completedOrder);
    }

    private List<Order> getOrders(OrderSearchRequest orderSearchRequest) {
        List<Order> orders;

        switch (orderSearchRequest.getOrderDivision()) {
            case BUYER -> orders = orderRepository.findAllByBuyer(orderSearchRequest.getMember());
            case SELLER -> orders = orderRepository.findAllBySeller(orderSearchRequest.getMember());
            default -> orders = orderRepository.findAllByMemberId(orderSearchRequest.getMember().getId());
        }
        return orders;
    }

    private void deductStockQuantities(Product product) {
        String productNumber = product.getProductNumber();
        Stock stock = stockRepository.findByProductNumber(productNumber);
        int quantity = stock.getQuantity();

        if (stock.isQuantityLessThan(quantity)) {
            throw new OutOfStockException("해당 상품에 재고가 부족합니다.");
        }
        stock.deductQuantity(quantity); // update
    }

    private void updateProductSellingStatus(Stock stock) {
        String productNumber = stock.getProductNumber();
        Product product = productRepository.findByProductNumber(productNumber);

        // 완료되지 않은 주문의 갯수
        long notCompletedOrderCnt = orderRepository.countNonCompletedOrdersByProductId(product.getId());

        // 재고가 0이지만 아직 주문대기인 상태가 있는 경우, 제품은 reserved
        if (stock.getQuantity() == 0 && notCompletedOrderCnt > 0) {
            product.reserved();
        }
        // 재고가 0이고 주문완료가 전부 완료되었다면, 제품은 sold out
        if (stock.getQuantity() == 0 && notCompletedOrderCnt == 0) {
            product.soldOut();
        }
    }

    private boolean isCompleteAllOrder(Product product) {
        long notCompletedOrderCnt = orderRepository.countNonCompletedOrdersByProductId(product.getId());
        return notCompletedOrderCnt == 0;
    }
}
