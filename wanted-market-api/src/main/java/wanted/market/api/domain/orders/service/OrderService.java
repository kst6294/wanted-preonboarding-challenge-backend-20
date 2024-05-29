package wanted.market.api.domain.orders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.market.api.domain.orders.dto.request.RegisterOrderRequestDto;
import wanted.market.api.domain.orders.dto.response.RegisterOrderResponseDto;
import wanted.market.api.domain.orders.entity.Order;
import wanted.market.api.domain.orders.repository.OrderRepository;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.product.repository.ProductRepository;
import wanted.market.api.domain.user.entity.User;
import wanted.market.api.domain.user.service.UserService;
import wanted.market.api.global.response.enums.ExceptionMessage;
import wanted.market.api.global.response.exception.WantedException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public RegisterOrderResponseDto orderProduct(RegisterOrderRequestDto requestDto) {
        User user = userService.getUser(requestDto.getUserId());
        Product product = productRepository.findById(requestDto.getProductId()).orElseThrow(()->new WantedException(ExceptionMessage.ISNOTEXIST));
        if(!requestDto.getPrice().equals(product.getPrice())) throw new WantedException(ExceptionMessage.HASCHANGED);
        Order order = Order.builder()
                .user(user)
                .product(product)
                .price(product.getPrice())
                .build();
        orderRepository.save(order);


        return RegisterOrderResponseDto.builder()
                .orderId(order.getId())
                .build();
    }
}
