package org.example.wantedmarket.service;

import org.example.wantedmarket.dto.order.OrderCreateRequest;
import org.example.wantedmarket.dto.order.OrderResponse;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.exception.ErrorCode;
import org.example.wantedmarket.model.Order;
import org.example.wantedmarket.model.Product;
import org.example.wantedmarket.model.User;
import org.example.wantedmarket.repository.OrderRepository;
import org.example.wantedmarket.repository.ProductRepository;
import org.example.wantedmarket.repository.UserRepository;
import org.example.wantedmarket.status.OrderStatus;
import org.example.wantedmarket.status.ProductStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
class OrderServiceTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    User buyer1;
    User buyer2;
    User seller1;
    User seller2;
    Product product1;

    @BeforeEach
    void setUp() {
        buyer1 = makeUser("buyer1", bCryptPasswordEncoder.encode("1234"), "ROLE_USER");
        buyer2 = makeUser("buyer2", bCryptPasswordEncoder.encode("1234"), "ROLE_USER");
        seller1 = makeUser("seller1", bCryptPasswordEncoder.encode("1234"), "ROLE_USER");
        seller2 = makeUser("seller2", bCryptPasswordEncoder.encode("1234"), "ROLE_USER");
        product1 = makeProduct("product1", 1000, 10, seller1, ProductStatus.FOR_SALE);
    }

    User makeUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    Product makeProduct(String name, Integer price, Integer quantity, User seller, ProductStatus status) {
        return productRepository.save(Product.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .seller(seller)
                .status(status)
                .build());
    }

    Order makeOrder(Product product, User buyer, Integer quantity, OrderStatus status) {
        return orderRepository.save(Order.builder()
                .product(product)
                .seller(product.getSeller())
                .buyer(buyer)
                .confirmedPrice(product.getPrice())
                .quantity(quantity)
                .status(status)
                .build());
    }

    @Test
    @DisplayName("제품 주문 - 추가 판매 가능한 수량이 남아있을 경우")
    @Transactional
    void orderProductTest_추가_판매_가능() {
        // given
        OrderCreateRequest request = new OrderCreateRequest();
        request.setProductId(product1.getId());
        request.setQuantity(2);

        int previousQuantity = product1.getQuantity();

        // when
        OrderResponse orderResponse = orderService.orderProduct(buyer2.getId(), request);

        // then
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(request.getProductId(), orderResponse.getProduct().getId());
        Assertions.assertEquals(request.getQuantity(), orderResponse.getQuantity());
        Assertions.assertEquals(product1.getSeller().getId(), orderResponse.getProduct().getSeller().getId());
        Assertions.assertEquals(buyer2.getId(), orderResponse.getBuyer().getId());

        Assertions.assertEquals(product1.getPrice(), orderResponse.getConfirmedPrice(), "확정된 가격이 주문 당시 제품 가격과 일치해야 합니다.");
        Assertions.assertEquals(OrderStatus.PENDING, orderResponse.getOrderStatus(), "주문 상태가 PENDING 이어야 합니다.");
        Assertions.assertEquals(ProductStatus.FOR_SALE, orderResponse.getProduct().getProductStatus(), "제품 상태가 FOR_SALE 이어야 합니다.");
        Assertions.assertEquals(previousQuantity - request.getQuantity(), orderResponse.getProduct().getQuantity(), "제품 수량이 올바르게 감소해야 합니다.");
    }

    @Test
    @DisplayName("제품 주문 - 추가 판매 가능한 수량이 없을 경우")
    @Transactional
    void orderProductTest_추가_판매_불가() {
        // given
        OrderCreateRequest request = new OrderCreateRequest();
        request.setProductId(product1.getId());
        request.setQuantity(10);

        // when
        OrderResponse orderResponse = orderService.orderProduct(buyer2.getId(), request);

        // then
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(request.getProductId(), orderResponse.getProduct().getId());
        Assertions.assertEquals(request.getQuantity(), orderResponse.getQuantity());
        Assertions.assertEquals(product1.getSeller().getId(), orderResponse.getProduct().getSeller().getId());
        Assertions.assertEquals(buyer2.getId(), orderResponse.getBuyer().getId());

        Assertions.assertEquals(product1.getPrice(), orderResponse.getConfirmedPrice(), "확정된 가격이 주문 당시 제품 가격과 일치해야 합니다.");
        Assertions.assertEquals(OrderStatus.PENDING, orderResponse.getOrderStatus(), "주문 상태가 PENDING 이어야 합니다.");
        Assertions.assertEquals(ProductStatus.IN_RESERVATION, orderResponse.getProduct().getProductStatus(), "제품 상태가 IN_RESERVATION 이어야 합니다.");
        Assertions.assertEquals(0, orderResponse.getProduct().getQuantity(),  "제품 수량이 0이어야 합니다.");
    }

    @Test
    @DisplayName("제품 주문 - 재고가 주문수량보다 부족할 경우")
    @Transactional
    void orderProductTest_재고_부족() {
        // given
        OrderCreateRequest request = new OrderCreateRequest();
        request.setProductId(product1.getId());
        request.setQuantity(11);

        // when
        CustomException customException = Assertions.assertThrows(CustomException.class, () ->
            orderService.orderProduct(buyer2.getId(), request)
        );

        // then
        Assertions.assertEquals(customException.getErrorCode(), ErrorCode.PRODUCT_NOT_ENOUGH);
    }

    @Test
    @DisplayName("제품 주문 - 제품이 예약중인 경우")
    @Transactional
    void orderProductTest_예약중인_제품일_경우() {
        // given
        product1.modifyQuantity(10);
        product1.modifyStatus(ProductStatus.IN_RESERVATION);

        OrderCreateRequest request = new OrderCreateRequest();
        request.setProductId(product1.getId());
        request.setQuantity(10);

        // when
        CustomException customException = Assertions.assertThrows(CustomException.class, () ->
                orderService.orderProduct(buyer1.getId(), request)
        );

        // then
        Assertions.assertEquals(customException.getErrorCode(), ErrorCode.PRODUCT_IN_RESERVATION);
    }

    @Test
    @DisplayName("제품 주문 - 제품이 판매완료된 경우")
    @Transactional
    void orderProductTest_판매완료된_제품일_경우() {
        // given
        product1.modifyQuantity(10);
        product1.modifyStatus(ProductStatus.SOLD_OUT);

        OrderCreateRequest request = new OrderCreateRequest();
        request.setProductId(product1.getId());
        request.setQuantity(10);

        // when
        CustomException customException = Assertions.assertThrows(CustomException.class, () ->
                orderService.orderProduct(buyer1.getId(), request)
        );

        // then
        Assertions.assertEquals(customException.getErrorCode(), ErrorCode.PRODUCT_SOLD_OUT);
    }

    @Test
    @DisplayName("제품 주문 - 본인의 제품을 주문한 경우")
    @Transactional
    void orderProductTest_본인_제품을_주문한_경우() {
        // given
        OrderCreateRequest request = new OrderCreateRequest();
        request.setProductId(product1.getId());
        request.setQuantity(10);

        // when
        CustomException customException = Assertions.assertThrows(CustomException.class, () ->
                orderService.orderProduct(seller1.getId(), request)
        );

        // then
        Assertions.assertEquals(customException.getErrorCode(), ErrorCode.ORDER_MY_PRODUCT_NOT_ALLOWED);
    }

    @Test
    @DisplayName("주문 승인 - 추가 판매 가능한 수량이 남아있을 경우")
    @Transactional
    void approveProductOrderTest_추가_판매_가능() {
        // given
        Order order = makeOrder(product1, buyer1, 5, OrderStatus.PENDING);
        product1.modifyQuantity(5);

        // when
        OrderResponse approvedOrder = orderService.approveProductOrder(seller1.getId(), order.getId());

        // then
        Assertions.assertEquals(approvedOrder.getOrderStatus(), OrderStatus.APPROVED);
        Assertions.assertEquals(approvedOrder.getProduct().getProductStatus(), ProductStatus.FOR_SALE);
    }

    @Test
    @DisplayName("주문 승인 - 추가 판매 가능한 수량이 없을 경우")
    @Transactional
    void approveProductOrderTest_추가_판매_불가() {
        // given
        Order order = makeOrder(product1, buyer1, 10, OrderStatus.PENDING);
        product1.modifyQuantity(10);
        product1.modifyStatus(ProductStatus.IN_RESERVATION);

        // when
        OrderResponse approvedOrder = orderService.approveProductOrder(seller1.getId(), order.getId());

        // then
        Assertions.assertEquals(approvedOrder.getOrderStatus(), OrderStatus.APPROVED);
        Assertions.assertEquals(approvedOrder.getProduct().getProductStatus(), ProductStatus.IN_RESERVATION);
    }

    @Test
    @DisplayName("주문 승인 - 주문을 승인하는 사용자가 판매자가 아닐 경우")
    @Transactional
    void approveProductOrderTest_판매자가_아닐_경우() {
        // given
        Order order = makeOrder(product1, buyer1, 10, OrderStatus.PENDING);
        product1.modifyQuantity(10);
        product1.modifyStatus(ProductStatus.IN_RESERVATION);

        // when
        CustomException customException = Assertions.assertThrows(CustomException.class, () ->
                orderService.approveProductOrder(seller2.getId(), order.getId())
        );

        // then
        Assertions.assertEquals(customException.getErrorCode(), ErrorCode.USER_NOT_SELLER);
    }

    @Test
    @DisplayName("주문 승인 - 판매 승인 가능한 주문이 아닐 경우")
    @Transactional
    void approveProductOrderTest_판매승인_상태가_아닐_경우() {
        // given
        Order order = makeOrder(product1, buyer1, 10, OrderStatus.APPROVED);
        product1.modifyQuantity(10);
        product1.modifyStatus(ProductStatus.IN_RESERVATION);

        // when
        CustomException customException = Assertions.assertThrows(CustomException.class, () ->
                orderService.approveProductOrder(seller1.getId(), order.getId())
        );

        // then
        Assertions.assertEquals(customException.getErrorCode(), ErrorCode.ORDER_NOT_PENDING);
    }

    @Test
    @DisplayName("구매 확정 - 추가 판매 가능한 수량이 남아있을 경우")
    @Transactional
    void confirmProductOrderTest_추가_판매_가능() {
         // given
        Order order = makeOrder(product1, buyer1, 5, OrderStatus.APPROVED);
        product1.modifyQuantity(5);

        // when
        OrderResponse confirmedOrder = orderService.confirmProductOrder(buyer1.getId(), order.getId());

        // then
        Assertions.assertEquals(confirmedOrder.getOrderStatus(), OrderStatus.CONFIRMED);
        Assertions.assertEquals(confirmedOrder.getProduct().getProductStatus(), ProductStatus.FOR_SALE);
    }

    @Test
    @DisplayName("구매 확정 - 추가 판매 가능한 수량이 없을 경우")
    @Transactional
    void confirmProductOrderTest_추가_판매_불가() {
        // given
        Order order = makeOrder(product1, buyer1, 10, OrderStatus.APPROVED);
        product1.modifyQuantity(10);

        // when
        OrderResponse confirmedOrder = orderService.confirmProductOrder(buyer1.getId(), order.getId());

        // then
        Assertions.assertEquals(confirmedOrder.getOrderStatus(), OrderStatus.CONFIRMED);
        Assertions.assertEquals(confirmedOrder.getProduct().getProductStatus(), ProductStatus.SOLD_OUT);
    }

    @Test
    @DisplayName("구매 확정 - 구매확정을 하는 사용자가 구매자가 아닐 경우")
    @Transactional
    void confirmProductOrderTest_구매자가_아닐_경우() {
        // given
         Order order = makeOrder(product1, buyer1, 5, OrderStatus.APPROVED);
        product1.modifyQuantity(5);

        // when
        CustomException customException = Assertions.assertThrows(CustomException.class, () ->
                orderService.confirmProductOrder(buyer2.getId(), order.getId())
        );

        // then
        Assertions.assertEquals(customException.getErrorCode(), ErrorCode.USER_NOT_BUYER);
    }

    @Test
    @DisplayName("구매 확정 - 구매확정할 수 있는 상태가 아닐 경우")
    @Transactional
    void confirmProductOrderTest_구매확정_가능한_상태가_아닐_경우() {
        // given
        Order order = makeOrder(product1, buyer1, 5, OrderStatus.CONFIRMED);
        product1.modifyQuantity(5);

        // when
        CustomException customException = Assertions.assertThrows(CustomException.class, () ->
                orderService.confirmProductOrder(buyer1.getId(), order.getId())
        );

        // then
        Assertions.assertEquals(customException.getErrorCode(), ErrorCode.ORDER_NOT_APPROVED);
    }

    @Test
    @DisplayName("내 거래내역 조회 - 성공")
    @Transactional
    void findMyTransactionListTest() {
        // given
        makeOrder(product1, buyer1, 5, OrderStatus.APPROVED);
        product1.modifyQuantity(5);


        // when
        List<OrderResponse> transactionList = orderService.findMyTransactionList(buyer1.getId());

        // then
        Assertions.assertEquals(transactionList.size(), 1);
    }

}