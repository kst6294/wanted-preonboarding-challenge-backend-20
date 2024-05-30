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
import wanted.market.api.domain.product.dto.internal.ProductInfoDto;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.product.enums.ProductStatus;
import wanted.market.api.domain.product.repository.ProductRepository;
import wanted.market.api.domain.user.dto.internal.UserInfoDto;
import wanted.market.api.domain.user.entity.User;
import wanted.market.api.domain.user.service.UserService;
import wanted.market.api.global.response.enums.ExceptionDomain;
import wanted.market.api.global.response.enums.ExceptionMessage;
import wanted.market.api.global.response.exception.WantedException;

import java.util.List;
import java.util.UnknownFormatConversionException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public RegisterOrderResponseDto orderProduct(HttpServletRequest request, RegisterOrderRequestDto requestDto) {
        User user = userService.getUser(request);
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(()->new WantedException(ExceptionDomain.ORDER, ExceptionMessage.IS_NOT_EXIST));
        if(user.getId().equals(product.getUser().getId())) throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.CANNOT_ORDER_OWN_PRODUCT);
        if(!product.getStatus().equals(ProductStatus.SALE)) throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.HAS_SOLD_OUT);
        if(!requestDto.getPrice().equals(product.getPrice())) throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.PRICE_HAS_CHANGED);
        Order order = Order.builder()
                .user(user)
                .product(product)
                .price(product.getPrice())
                .build();
        orderRepository.save(order);
        product.reserve();
        return RegisterOrderResponseDto.builder()
                .orderId(order.getId())
                .build();
    }

    public OrderListResponseDto searchBuyOrderList(HttpServletRequest request, Long targetUserId) {
        User user = userService.getUser(request);
        List<Order> orders;
        if(targetUserId==null) orders = orderRepository.findAllByUserId(user.getId());
        else orders = orderRepository.findAllByUserIdAndProductUserId(user.getId(), targetUserId);

        List<OrderInfoDto> orderInfoDtos = orders.stream()
                .map(order ->
                        OrderInfoDto.fromOrderAndDtos(order,ProductInfoDto.fromProductAndUser(order.getProduct(),  UserInfoDto.fromProduct(order.getProduct())))
                ).toList();

        return OrderListResponseDto.builder().orderInfo(orderInfoDtos).build();
    }

    public OrderListResponseDto searchSellOrderList(HttpServletRequest request, Long targetUserId) {
        User user = userService.getUser(request);
        List<Order> orders;
        if(targetUserId==null) orders = orderRepository.findAllByProductUserId(user.getId());
        else orders = orderRepository.findAllByUserIdAndProductUserId(targetUserId, user.getId());
        List<OrderInfoDto> orderInfoDtos = orders.stream()
                .map(order ->
                        OrderInfoDto.fromOrderAndDtos(order,ProductInfoDto.fromProductAndUser(order.getProduct(),  UserInfoDto.fromProduct(order.getProduct())))
                ).toList();

        return OrderListResponseDto.builder().orderInfo(orderInfoDtos).build();
    }

    @Transactional
    public ApproveOrderResponseDto approveOrder(HttpServletRequest request, ApproveOrderRequestDto requestDto) {
        User user = userService.getUser(request);
        Order order = orderRepository.findById(requestDto.getOrderId()).orElseThrow(()->new WantedException(ExceptionDomain.ORDER, ExceptionMessage.IS_NOT_EXIST));
        Product product = order.getProduct();
        if(!user.getId().equals(product.getUser().getId())) throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.IS_NOT_OWNER);
        if(!order.getStatus().equals(OrderStatus.RESERVED)) throw new WantedException(ExceptionDomain.ORDER, ExceptionMessage.IS_NOT_RESERVED);
        order.approve();
        product.complete();
        return ApproveOrderResponseDto.builder().orderId(order.getId()).build();
    }
}
