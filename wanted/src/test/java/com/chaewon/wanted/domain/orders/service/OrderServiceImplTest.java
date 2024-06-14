package com.chaewon.wanted.domain.orders.service;

import com.chaewon.wanted.domain.member.entity.Member;
import com.chaewon.wanted.domain.member.repository.MemberRepository;
import com.chaewon.wanted.domain.orders.dto.request.OrderRequestDto;
import com.chaewon.wanted.domain.orders.entity.OrderStatus;
import com.chaewon.wanted.domain.orders.entity.Orders;
import com.chaewon.wanted.domain.orders.repository.OrderRepository;
import com.chaewon.wanted.domain.product.dto.request.ModifyRequestDto;
import com.chaewon.wanted.domain.product.entity.Product;
import com.chaewon.wanted.domain.product.entity.ProductStatus;
import com.chaewon.wanted.domain.product.exception.ProductUnavailableException;
import com.chaewon.wanted.domain.product.repository.ProductRepository;
import com.chaewon.wanted.domain.product.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Member seller;
    private Member buyer;
    private Product product;

    @BeforeEach
    void init() {
        seller = Member.builder()
                .username("판매자2")
                .email("seller2@test.com")
                .password("1234")
                .build();
        memberRepository.saveAndFlush(seller);

        buyer = Member.builder()
                .username("구매자2")
                .email("buyer2@test.com")
                .password("1234")
                .build();
        memberRepository.saveAndFlush(buyer);

        product = Product.builder()
                .name("A2제품")
                .price(3000)
                .productStatus(ProductStatus.판매중)
                .quantity(3)
                .member(seller)
                .build();
        productRepository.saveAndFlush(product);

    }

    /* case 1. createOrder메소드와 modifyProduct 메소드 동시성 테스트
            - 원래 판매 가격(price) 5,000원
            - 구매자가 구매 요청을 하는 것과 "동시에" 판매자가 가격(price)을 10,000원으로 변경함
            -> 구매 객체의 가격은 (order.getOrderPrice()) 5,000원으로 저장

            ! 데드락 발생 (구매요청이 동시에 이루어졌을 때 상황에 대비해 비관적 락을 적용해주었더니 발생함 -> 재시도 로직을 추가하여 해결 -> 성능적으로 낙관적이 좋을 것으로 판단
    */
    @Test
    @Order(1)
    @DisplayName("구매 요청과 동시에 판매자가 가격을 변경했을 때, 구매 요청 시 보았던 가격으로 주문이 이루어진다.")
    void 가격변경시동시성문제테스트() throws Exception {
        // Given
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .productId(product.getId())
                .build();
        ModifyRequestDto modifyRequestDto = ModifyRequestDto.builder()
                .price(10000)
                .build();

        // 스레드 1: 구매 요청
        CompletableFuture<Void> orderTask = CompletableFuture.runAsync(() -> {
            try {
                orderService.createOrder(buyer.getEmail(), orderRequestDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 스레드 2: 가격 수정
        CompletableFuture<Void> modifyTask = CompletableFuture.runAsync(() -> {
            try {
                productService.modifyProduct(seller.getEmail(), product.getId(), modifyRequestDto);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 두 스레드가 완료될 때까지 대기
        CompletableFuture.allOf(orderTask, modifyTask).join();

        // Then: 구매 요청의 가격이 수정 전 가격인지 확인
        List<Orders> orders = orderRepository.findAll();
        assertEquals(1, orders.size());
        Orders order = orders.get(0);

        assertEquals(3000, order.getOrderPrice(), "구매 요청 시 보았던 가격으로 주문이 이루어져야 한다.");
    }

    @Test
    @Order(2)
    @DisplayName("여러 구매 요청과 동시에 판매자가 가격을 변경했을 때, 구매 요청 시 보았던 가격으로 주문이 이루어진다.")
    void 여러개의구매요청에서가격변경시동시성문제테스트() throws Exception {
        // Given
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .productId(product.getId())
                .build();
        ModifyRequestDto modifyRequestDto = ModifyRequestDto.builder()
                .price(10000)
                .build();

        int Buyers = 1000; // 동시 구매 요청 수
        ExecutorService executorService = Executors.newFixedThreadPool(Buyers + 1);
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(Buyers + 1); // 모든 작업 완료 대기용

        // 구매 요청 스레드 생성
        for (int i = 0; i < Buyers; i++) {
            executorService.submit(() -> {
                try {
                    latch.await(); // 모든 스레드가 준비될 때까지 대기
                    orderService.createOrder(buyer.getEmail(), orderRequestDto);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    doneLatch.countDown(); // 작업 완료 후 카운트 다운
                }
            });
        }

        // 가격 수정 스레드 생성
        executorService.submit(() -> {
            try {
                latch.await(); // 모든 스레드가 준비될 때까지 대기
                productService.modifyProduct(seller.getEmail(), product.getId(), modifyRequestDto);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                doneLatch.countDown(); // 작업 완료 후 카운트 다운
            }
        });

        // 모든 스레드 시작
        latch.countDown();
        // 모든 작업 완료 대기
        doneLatch.await();

        // Then: 구매 요청의 가격이 수정 전 가격인지 확인
        List<Orders> orders = orderRepository.findAll();
        for (Orders order : orders) {
            assertEquals(3000, order.getOrderPrice(), "구매 요청 시 보았던 가격으로 주문이 이루어져야 한다.");
        }
    }

    /* case 2. 구매 요청과 판매 요청 동작 테스트
            - 원래 판매 가격(price) 3,000원
            - 판매자가 가격(price)을 6,000원으로 변경함
            - 이 후 구매자가 구매 요청을 함
            -> 구매 객체의 가격은 (order.getOrderPrice()) 6,000원으로 저장
    */
    @Test
    @Order(3)
    @DisplayName("구매 요청을 보내기 전에 판매자가 가격을 변경했을 때, 변경된 가격으로 주문이 이루어진다.")
    void 가격변경후구매테스트() {

        // Given : 1)상품의 가격을 변경하고, 2) 주문 요청을 생성
        ModifyRequestDto modifyRequestDto = ModifyRequestDto.builder()
                .price(6000)
                .build();
        productService.modifyProduct(seller.getEmail(), product.getId(), modifyRequestDto);

        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .productId(product.getId())
                .build();

        // When : 주문 생성
        orderService.createOrder(buyer.getEmail(), orderRequestDto);

        /*
        Then : 생성된 주문 가격이 변경된 가격인지 검증
            1) 주문 목록 조회 2) 생성된 주문 객체가 1개인지 검증 3) 주문 객체 저장 4) 주문 가격이 변경된 가격인지 검증
        */
        List<Orders> orders = orderRepository.findAll();
        assertEquals(1, orders.size());
        Orders order = orders.get(0);

        assertEquals(6000, order.getOrderPrice());
    }

    /* case 3. 구매 요청이 동시에 발생했을 때, 주문 생성 테스트
            - 주문 생성은 제품 수량 이하로 생성 가능하고,
            - 초과된 구매 요청은 주문 객체 생성 x & ProductUnavailableException을 발생시킴
            -> 비관적/낙관적 락 적용하여 해결
    */
    @Test
    @Order(4)
    @Transactional
    @DisplayName("주문 생성은 제품의 수량에 맞게 제한되고, 초과된 구매 요청은 ProductUnavailableException을 발생시켜서 주문객체를 생성 못하게")
    void 재고관리동시성문제테스트() throws InterruptedException {
        // Given
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .productId(product.getId())
                .build();

        int threadCount = 2000;  // 시도할 스레드 수
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.createOrder(buyer.getEmail(), orderRequestDto);
                } catch (ProductUnavailableException e) {
                    // 예상된 예외이므로 무시
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // Then : pendingOrderCount는 실제로 성공적으로 생성된 주문의 수(구매 요청을 받았지만 아직 거래가 확정되지 않은 주문의 수)
        long pendingOrderCount = orderRepository.countByProductAndOrderStatusNot(product, OrderStatus.거래확정);
        assertThat(pendingOrderCount).isLessThanOrEqualTo(product.getQuantity());
    }

}

