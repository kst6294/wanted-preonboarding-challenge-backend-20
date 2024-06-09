package com.example.wanted.order.service;

import com.example.wanted.mock.FakeOrderRepository;
import com.example.wanted.mock.FakeProductRepository;
import com.example.wanted.mock.FakeUserRepository;
import com.example.wanted.module.exception.AlreadyOrderException;
import com.example.wanted.module.exception.ProductNotAvailableException;
import com.example.wanted.module.exception.ResourceNotFoundException;
import com.example.wanted.module.exception.SellerCannotMakeOrderException;
import com.example.wanted.order.domain.Order;
import com.example.wanted.order.domain.OrderCreate;
import com.example.wanted.order.domain.OrderStatus;
import com.example.wanted.order.service.response.OrderResponse;
import com.example.wanted.product.domain.Product;
import com.example.wanted.product.domain.ProductSellingStatus;
import com.example.wanted.user.domain.Role;
import com.example.wanted.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    private FakeProductRepository fakeProductRepository;
    private FakeUserRepository fakeUserRepository;
    private FakeOrderRepository fakeOrderRepository;

    @BeforeEach
    void init() {
        this.fakeUserRepository = new FakeUserRepository();
        this.fakeOrderRepository = new FakeOrderRepository();
        this.fakeProductRepository = new FakeProductRepository();
        this.orderService = OrderService.builder()
                .orderRepository(fakeOrderRepository)
                .productRepository(fakeProductRepository)
                .userRepository(fakeUserRepository)
                .build();
    }

    @Test
    void OrderCreate로_order를_생성할_수_있다(){
        //given
        User seller = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        User buyer = User.builder()
                .id(2L)
                .account("test12@gmail.com")
                .password("test1234")
                .name("세종대왕")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        Product product1 = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(100)
                .price(1000)
                .seller(seller)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product1);

        OrderCreate orderCreate = OrderCreate.builder()
                .productId(1L)
                .build();

        //when
        OrderResponse result = orderService.order(orderCreate, buyer.getId());

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getBuyer().getId()).isEqualTo(2L);
        assertThat(result.getSeller().getId()).isEqualTo(1L);
        assertThat(result.getProductId()).isEqualTo(1L);
//        assertThat(result.getProduct().getQuantity()).isEqualTo(99);
        assertThat(result.getPrice()).isEqualTo(1000);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.REQUEST);
    }

    @Test
    void 주문생성_시_재고가_1개_남아있었으면_제품의_상태가_예약중으로_변경된다(){
        //given
        User seller = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        User buyer = User.builder()
                .id(2L)
                .account("test12@gmail.com")
                .password("test1234")
                .name("세종대왕")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        Product product1 = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(1)
                .price(1000)
                .seller(seller)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product1);

        OrderCreate orderCreate = OrderCreate.builder()
                .productId(1L)
                .build();

        //when
        OrderResponse result = orderService.order(orderCreate, buyer.getId());

        //then
        Product product = fakeProductRepository.findById((result.getProductId())).orElseThrow();
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getSellingStatus()).isEqualTo(ProductSellingStatus.RESERVATION);
    }

    @Test
    void 판매자는_주문을_생성할_수_없다(){
        //given
        User seller = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(100)
                .price(1000)
                .seller(seller)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product);

        OrderCreate orderCreate = OrderCreate.builder()
                .productId(1L)
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                orderService.order(orderCreate, seller.getId())
        ).isInstanceOf(SellerCannotMakeOrderException.class);
    }

    @Test
    void 회원이_아니면_주문을_생성할_수_없다(){
        //given
        User seller = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(100)
                .price(1000)
                .seller(seller)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product);

        OrderCreate orderCreate = OrderCreate.builder()
                .productId(1L)
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                orderService.order(orderCreate, 2L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 존재하지_않는_제품은_주문을_생성할_수_없다(){
        //given
        User seller = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(100)
                .price(1000)
                .seller(seller)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product);

        OrderCreate orderCreate = OrderCreate.builder()
                .productId(2L)
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                orderService.order(orderCreate, 1L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 회원이_해당_제품을_구매한_적이_있으면_주문할_수_없다(){
        //given
        User seller = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        User buyer = User.builder()
                .id(2L)
                .account("test12@gmail.com")
                .password("test1234")
                .name("세종대왕")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        Product product = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(100)
                .price(1000)
                .seller(seller)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order);

        OrderCreate orderCreate = OrderCreate.builder()
                .productId(1L)
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                orderService.order(orderCreate, 2L)
        ).isInstanceOf(AlreadyOrderException.class);
    }

    @Test
    void 제고가_없으면_주문할_수_없다(){
        //given
        User seller = User.builder()
                .id(1L)
                .account("test@gmail.com")
                .password("test1234")
                .name("홍길동")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("아이스크림")
                .quantity(0)
                .price(1000)
                .seller(seller)
                .sellingStatus(ProductSellingStatus.SELLING)
                .build();
        fakeProductRepository.save(product);

        OrderCreate orderCreate = OrderCreate.builder()
                .productId(1L)
                .build();

        //when
        //then
        assertThatThrownBy(() ->
                orderService.order(orderCreate, 1L)
        ).isInstanceOf(ProductNotAvailableException.class);
    }

    @Test
    void 판매자는_주문을_승인할_수_있다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order);

        //when
        OrderResponse result = orderService.approve(1L, 2L);

        //then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.APPROVAL);
    }

    @Test
    void 회원이_아니면_승인할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.approve(1L, 3L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 없는_주문은_승인할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.approve(2L, 2L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 판매자가_아니면_승인할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.approve(1L, 1L)
        ).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void 구매자가_판매_승인된_주문을_구매확정_할_수_있다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(0)
                .sellingStatus(ProductSellingStatus.RESERVATION)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.APPROVAL)
                .build();
        fakeOrderRepository.save(order);

        //when
        OrderResponse result = orderService.confirmation(1L, 1L);

        //then
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PURCHASE_CONFIRMATION);
    }

    @Test
    void 구매_확정을_할_때_모든_주문이_확정되었으면_제품의_상태가_완료로_변경된다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(0)
                .sellingStatus(ProductSellingStatus.RESERVATION)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.APPROVAL)
                .build();
        fakeOrderRepository.save(order);

        //when
        OrderResponse result = orderService.confirmation(1L, 1L);

        //then
        Product product1 = fakeProductRepository.findById(result.getProductId()).orElseThrow();
        assertThat(result.getStatus()).isEqualTo(OrderStatus.PURCHASE_CONFIRMATION);
        assertThat(product1.getSellingStatus())
                .isEqualTo(ProductSellingStatus.COMPLETE);
    }

    @Test
    void 구매자가_아니면_주문_확정할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.APPROVAL)
                .build();
        fakeOrderRepository.save(order);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.confirmation(1L, 2L)
        ).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void 주문상태가_판매_승인_상태가_아니면_주문확정할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.APPROVAL)
                .build();
        fakeOrderRepository.save(order);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.confirmation(1L, 2L)
        ).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void 주문이_구매요청_상태가_아니면_승인할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product);

        Order order = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .status(OrderStatus.APPROVAL)
                .build();
        fakeOrderRepository.save(order);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.approve(1L, 2L)
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    void Id로_주문을_조회할_수_있다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product1 = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("로지텍G SuperLight")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product2);

        Order order1 = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product1)
                .price(product1.getPrice())
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order1);

        Order order2 = Order.builder()
                .id(2L)
                .seller(buyer)
                .buyer(seller)
                .product(product2)
                .price(product2.getPrice())
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order2);

        //when
        OrderResponse result = orderService.getById(1L);

        //then
        Product product = fakeProductRepository.findById(result.getProductId()).orElseThrow();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getBuyer().getId()).isEqualTo(1L);
        assertThat(result.getSeller().getId()).isEqualTo(2L);
        assertThat(product.getId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualTo(150000);
        assertThat(result.getStatus()).isEqualTo(OrderStatus.REQUEST);
    }

    @Test
    void 존재하지_않는_주문Id로는_조회할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product1 = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("로지텍G SuperLight")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product2);

        Order order1 = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product1)
                .price(product1.getPrice())
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order1);

        Order order2 = Order.builder()
                .id(2L)
                .seller(buyer)
                .buyer(seller)
                .product(product2)
                .price(product2.getPrice())
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order2);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.getById(3L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userId로_주문_리스트를_조회할_수_있다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product1 = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("로지텍G SuperLight")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product2);

        Order order1 = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product1)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order1);

        Order order2 = Order.builder()
                .id(2L)
                .seller(buyer)
                .buyer(seller)
                .product(product2)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order2);

        //when
        List<OrderResponse> result = orderService.getByUserId(1L);

        //then
        assertThat(result).hasSize(2);
    }

    @Test
    void 비회원은_자신의_주문_리스트를_조회할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product1 = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("로지텍G SuperLight")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product2);

        Order order1 = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product1)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order1);

        Order order2 = Order.builder()
                .id(2L)
                .seller(buyer)
                .buyer(seller)
                .product(product2)
                .status(OrderStatus.REQUEST)
                .build();
        fakeOrderRepository.save(order2);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.getByUserId(3L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userId와_productId로_주문을_조회할_수_있다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product1 = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("로지텍G SuperLight")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product2);

        Order order1 = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product1)
                .status(OrderStatus.REQUEST)
                .price(product1.getPrice())
                .build();
        fakeOrderRepository.save(order1);

        Order order2 = Order.builder()
                .id(2L)
                .seller(buyer)
                .buyer(seller)
                .product(product2)
                .status(OrderStatus.REQUEST)
                .price(product2.getPrice())
                .build();
        fakeOrderRepository.save(order2);

        //when
        List<OrderResponse> result = orderService.getByUserIdAndProductId(2L,1L);

        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isNotNull();
        assertThat(result.get(0).getBuyer().getId()).isEqualTo(1L);
        assertThat(result.get(0).getSeller().getId()).isEqualTo(2L);
        assertThat(result.get(0).getProductId()).isEqualTo(1L);
        assertThat(result.get(0).getPrice()).isEqualTo(150000);
        assertThat(result.get(0).getStatus()).isEqualTo(OrderStatus.REQUEST);
    }

    @Test
    void 회원이_아니면_userId와_productId로_주문을_조회할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product1 = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("로지텍G SuperLight")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product2);

        Order order1 = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product1)
                .status(OrderStatus.REQUEST)
                .price(product1.getPrice())
                .build();
        fakeOrderRepository.save(order1);

        Order order2 = Order.builder()
                .id(2L)
                .seller(buyer)
                .buyer(seller)
                .product(product2)
                .status(OrderStatus.REQUEST)
                .price(product2.getPrice())
                .build();
        fakeOrderRepository.save(order2);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.getByUserIdAndProductId(3L,1L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userId와_productId로_조회할_때_없는_제품은_주문을_조회할_수_없다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product1 = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("로지텍G SuperLight")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product2);

        Order order1 = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product1)
                .status(OrderStatus.REQUEST)
                .price(product1.getPrice())
                .build();
        fakeOrderRepository.save(order1);

        Order order2 = Order.builder()
                .id(2L)
                .seller(buyer)
                .buyer(seller)
                .product(product2)
                .status(OrderStatus.REQUEST)
                .price(product2.getPrice())
                .build();
        fakeOrderRepository.save(order2);

        //when
        //then
        assertThatThrownBy(() ->
                orderService.getByUserIdAndProductId(2L,3L)
        ).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 제품의_거래내역이_없으면_빈_값이_조회된다(){
        //given
        User buyer = User.builder()
                .id(1L)
                .name("홍길동")
                .account("buy@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(buyer);

        User seller = User.builder()
                .id(2L)
                .name("엄꺽정")
                .account("seller@gmail.com")
                .password("test1234")
                .role(Role.USER)
                .build();
        fakeUserRepository.save(seller);

        Product product1 = Product.builder()
                .id(1L)
                .name("로지텍G SuperLight2")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product1);

        Product product2 = Product.builder()
                .id(2L)
                .name("로지텍G SuperLight")
                .price(150000)
                .quantity(10)
                .sellingStatus(ProductSellingStatus.SELLING)
                .seller(seller)
                .build();
        fakeProductRepository.save(product2);

        Order order1 = Order.builder()
                .id(1L)
                .seller(seller)
                .buyer(buyer)
                .product(product1)
                .status(OrderStatus.REQUEST)
                .price(product1.getPrice())
                .build();
        fakeOrderRepository.save(order1);

        //when
        List<OrderResponse> result = orderService.getByUserIdAndProductId(2L,2L);

        //then
        assertThat(result).hasSize(0);
    }

}