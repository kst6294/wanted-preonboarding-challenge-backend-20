package com.wanted.preonboarding.backend20.domain.order.application;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import com.wanted.preonboarding.backend20.domain.member.repository.MemberRepository;
import com.wanted.preonboarding.backend20.domain.order.domain.Order;
import com.wanted.preonboarding.backend20.domain.order.enums.OrderStatus;
import com.wanted.preonboarding.backend20.domain.order.repository.OrderRepository;
import com.wanted.preonboarding.backend20.domain.product.domain.Product;
import com.wanted.preonboarding.backend20.domain.product.dto.ProductRequestDto;
import com.wanted.preonboarding.backend20.domain.product.enums.ProductStatus;
import com.wanted.preonboarding.backend20.domain.product.repository.ProductRepository;
import com.wanted.preonboarding.backend20.global.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Member seller;
    private Member buyer;
    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        seller = new Member(1L, "seller@naver.com", "asdf", "판매자", Collections.singletonList("ROLE_USER"));
        buyer = new Member(2L, "buyer@naver.com", "asdf", "구매자", Collections.singletonList("ROLE_USER"));

        product = new Product(new ProductRequestDto("sldf", 1000, "sdfk", 1), seller);
        order = Order.builder()
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .build();
    }

    @Test
    @DisplayName("주문 - 재고 - 1 && 제품 예약중 상태로 변경")
    void orderProduct_product_status_reserved() {
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(product));
        orderService.orderProduct(1L, buyer);
        Assertions.assertThat(product.getTotalQuantity()).isEqualTo(0);
        Assertions.assertThat(product.getStatus()).isEqualTo(ProductStatus.RESERVED);
    }

    @Test
    @DisplayName("주문 - 동시에 100개 요청")
    void orderProduct_multi_100() throws InterruptedException {
        Product product2 = new Product(new ProductRequestDto("222", 1000, "sdfk", 100), seller);
        when(productRepository.findById(2L)).thenReturn(Optional.of(product2));

        ExecutorService service = Executors.newFixedThreadPool(34);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            service.execute(() -> {
                try {
                    orderService.orderProduct(2L, buyer);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Assertions.assertThat(product2.getTotalQuantity()).isEqualTo(0);
        Assertions.assertThat(product2.getStatus()).isEqualTo(ProductStatus.RESERVED);
    }

    @Test
    @DisplayName("판매자가 아닌 사람이 판매승인 - 에러 발생")
    void approveSellersOrderThrowError() {
        Member test = new Member(3L, "test@naver.com", "asdf", "테스트", Collections.singletonList("ROLE_USER"));

        Product product = new Product(new ProductRequestDto("sldf", 1000, "sdfk", 1), seller);
        Order order = Order.builder()
                .seller(seller)
                .buyer(buyer)
                .product(product)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order));
        Assertions.assertThatThrownBy(() -> orderService.approveSellersOrder(1L, test))
                .isExactlyInstanceOf(CustomException.class)
                .hasMessageContaining("불가능");
    }

    @Test
    @DisplayName("판매자가 판매승인")
    void approveSellersOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order));
        orderService.approveSellersOrder(1L, seller);
        assert order != null;
        Assertions.assertThat(order.getSellerOrderStatus()).isEqualTo(OrderStatus.SELLER_APPROVAL);
    }

    @Test
    @DisplayName("구매자가 구매확인 진행")
    void confirmBuyersOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order));
        orderService.approveSellersOrder(1L, seller);
        orderService.confirmBuyersOrder(1L, buyer);
        Assertions.assertThat(order.getBuyerOrderStatus()).isEqualTo(OrderStatus.BUYER_ORDER_CONFIRM);
    }

    @Test
    @DisplayName("구매확정 시 아직 판매자가 판매승인을 하지 않음")
    void confirmBuyersOrderBeforeSellersApproval() {
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order));

        Assertions.assertThatThrownBy(() -> orderService.confirmBuyersOrder(1L, buyer))
                .isExactlyInstanceOf(CustomException.class)
                .hasMessageContaining("판매승인");
    }

    @Test
    @DisplayName("제품의 모든 주문 구매확정 진행됨, 제품 soldout 처리")
    void confirmBuyersOrder_product_all_sold_out() {
        product.productAllReserved();

        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order));
        when(orderRepository.existsByProductIdAndBuyerOrderStatusEquals(null, OrderStatus.PRE)).thenReturn(false);

        orderService.approveSellersOrder(1L, seller);
        orderService.confirmBuyersOrder(1L, buyer);
        Assertions.assertThat(product.getStatus()).isEqualTo(ProductStatus.SOLD);
    }
}