package wanted.market.api.domain.orders.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.market.api.domain.orders.dto.internal.OrderInfoDto;
import wanted.market.api.domain.orders.dto.request.ApproveOrderRequestDto;
import wanted.market.api.domain.orders.dto.request.RegisterOrderRequestDto;
import wanted.market.api.domain.orders.dto.response.ApproveOrderResponseDto;
import wanted.market.api.domain.orders.dto.response.OrderListResponseDto;
import wanted.market.api.domain.orders.dto.response.RegisterOrderResponseDto;
import wanted.market.api.domain.orders.entity.Order;
import wanted.market.api.domain.orders.enums.OrderStatus;
import wanted.market.api.domain.orders.repository.OrderRepository;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.product.enums.ProductStatus;
import wanted.market.api.domain.product.repository.ProductRepository;
import wanted.market.api.domain.user.entity.User;
import wanted.market.api.domain.user.service.UserService;
import wanted.market.api.global.response.enums.ExceptionDomain;
import wanted.market.api.global.response.enums.ExceptionMessage;
import wanted.market.api.global.response.exception.WantedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public RegisterOrderResponseDto orderProduct(HttpServletRequest request, RegisterOrderRequestDto requestDto) {
        User user = userService.getUser(request);
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(() -> new WantedException(ExceptionDomain.ORDER, ExceptionMessage.IS_NOT_EXIST));
        if (user.getId().equals(product.findUserId())) {
            throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.CANNOT_ORDER_OWN_PRODUCT);
        }
        if (!product.getStatus().equals(ProductStatus.SALE)) {
            throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.HAS_SOLD_OUT);
        }
        if (!requestDto.getPrice().equals(product.getPrice())) {
            throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.PRICE_HAS_CHANGED);
        }
        Order order = Order.of(product, user, requestDto.getPrice(), requestDto.getCount());
        orderRepository.save(order);
        product.reserve();
        return RegisterOrderResponseDto.of(order.getId());
    }

    public OrderListResponseDto searchBuyOrderList(HttpServletRequest request, Long targetUserId, String status) {
        User user = userService.getUser(request);
        OrderStatus orderStatus = OrderStatus.addDefaultValueOf(status.toUpperCase());
        List<Order> orders;
        if (targetUserId == null) orders = orderRepository.findAllByUserIdAndStatus(user.getId(), orderStatus);
        else orders = orderRepository.findAllByUserIdAndProductUserIdAndStatus(user.getId(), targetUserId, orderStatus);
        List<OrderInfoDto> orderInfoDtos = orders.stream()
                .map(OrderInfoDto::from)
                .toList();
        return OrderListResponseDto.of(orderInfoDtos);
    }

    public OrderListResponseDto searchSellOrderList(HttpServletRequest request, Long targetUserId, String status) {
        User user = userService.getUser(request);
        OrderStatus orderStatus = OrderStatus.addDefaultValueOf(status.toUpperCase());
        List<Order> orders;
        if (targetUserId == null) {orders = orderRepository.findAllByProductUserIdAndStatus(user.getId(), orderStatus);}
        else {orders = orderRepository.findAllByUserIdAndProductUserIdAndStatus(targetUserId, user.getId(), orderStatus);}
        List<OrderInfoDto> orderInfoDtos = orders.stream()
                .map(OrderInfoDto::from)
                .toList();
        return OrderListResponseDto.of(orderInfoDtos);
    }

    @Transactional
    public ApproveOrderResponseDto approveOrder(HttpServletRequest request, ApproveOrderRequestDto requestDto) {
        User user = userService.getUser(request);
        Order order = orderRepository.findById(requestDto.getOrderId()).orElseThrow(() -> new WantedException(ExceptionDomain.ORDER, ExceptionMessage.IS_NOT_EXIST));
        Product product = order.getProduct();
        if (!user.getId().equals(product.getUser().getId())) {
            throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.IS_NOT_OWNER);
        }
        if (!order.getStatus().equals(OrderStatus.RESERVED)) {
            throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.IS_NOT_RESERVED);
        }
        order.approve();
        product.complete();
        return ApproveOrderResponseDto.of(order.getId());
    }
}
