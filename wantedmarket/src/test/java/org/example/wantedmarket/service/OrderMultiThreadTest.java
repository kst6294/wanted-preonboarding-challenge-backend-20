package org.example.wantedmarket.service;

import org.example.wantedmarket.dto.order.OrderCreateRequest;
import org.example.wantedmarket.exception.CustomException;
import org.example.wantedmarket.exception.ErrorCode;
import org.example.wantedmarket.domain.Order;
import org.example.wantedmarket.domain.Product;
import org.example.wantedmarket.domain.User;
import org.example.wantedmarket.repository.jpa.OrderJpaRepository;
import org.example.wantedmarket.repository.ProductRepository;
import org.example.wantedmarket.repository.jpa.UserJpaRepository;
import org.example.wantedmarket.status.OrderStatus;
import org.example.wantedmarket.status.ProductStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


@SpringBootTest
public class OrderMultiThreadTest {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserJpaRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderJpaRepository orderRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void clear() {
        orderRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("여러 명의 사용자가 순차적으로 주문했을 경우")
    void orderProductTest_다수가_순차적으로_주문() {
        // given
        int buyerCount = 10;
        int quantityCount = 10;
        int successCount = 0;
        int failCount = 0;

        User seller = makeUser("seller", bCryptPasswordEncoder.encode("1234"), "ROLE_USER");
        Product product = makeProduct("product", 1000, quantityCount, seller, ProductStatus.FOR_SALE);

        // when
        for (int i = 0; i < buyerCount; i++) {
            try {
                User buyer = makeUser("buyer" + i, bCryptPasswordEncoder.encode("1234"), "ROLE_USER");
                OrderCreateRequest request = new OrderCreateRequest();
                request.setProductId(product.getId());
                request.setQuantity(2);

                orderService.orderProduct(buyer.getId(), request);
                Product findProduct = productRepository.findById(request.getProductId()).orElseThrow(
                        () ->  new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
                System.out.println("product quantity = " + findProduct.getQuantity());

                successCount += 1;
            } catch (Exception e) {
                failCount += 1;
            }
        }

        System.out.println("successCount = " + successCount);
        System.out.println("failCount = " + failCount);

        // then
        Long orderCount = orderRepository.count();
        System.out.println("orderCount = " + orderCount);
        Assertions.assertEquals(5, successCount);
    }

    @Test
    @DisplayName("여러 명의 사용자가 동시에 주문했을 경우")
    void orderProductTest_다수가_동시에_주문() throws InterruptedException{
        // given
        int buyerCount = 10;
        int quantityCount = 10;

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        User seller = makeUser("seller", bCryptPasswordEncoder.encode("1234"), "ROLE_USER");
        Product product = makeProduct("product", 1000, quantityCount, seller, ProductStatus.FOR_SALE);

        ExecutorService executorService = Executors.newFixedThreadPool(buyerCount);
        CountDownLatch latch = new CountDownLatch(buyerCount);

        // when
        for (int i = 0; i < buyerCount; i++) {
            final int buyerIndex = i;
            executorService.submit(() -> {
                try {
                    User buyer = makeUser("buyer" + buyerIndex, bCryptPasswordEncoder.encode("1234"), "ROLE_USER");
                    OrderCreateRequest request = new OrderCreateRequest();
                    request.setProductId(product.getId());
                    request.setQuantity(2);

                    orderService.orderProduct(buyer.getId(), request);
                    Product findProduct = productRepository.findById(request.getProductId()).orElseThrow(
                            () ->  new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
                    System.out.println("product quantity = " + findProduct.getQuantity());

                    successCount.incrementAndGet();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        System.out.println("successCount = " + successCount);
        System.out.println("failCount = " + failCount);

        // then
        Assertions.assertEquals(5, successCount.get());
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

}
