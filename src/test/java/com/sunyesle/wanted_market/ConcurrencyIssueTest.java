package com.sunyesle.wanted_market;

import com.sunyesle.wanted_market.product.Product;
import com.sunyesle.wanted_market.product.ProductRepository;
import com.sunyesle.wanted_market.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class ConcurrencyIssueTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void 제품_재고_동시성_이슈_테스트() throws InterruptedException {
        Product product = new Product(1L, "상품명", 10000, 100);
        productRepository.saveAndFlush(product);

        log.info("=============== 예약 ===============");
        // 예약
        makeReservationConcurrency(product.getId(), 100);

        // 예약 결과 검증
        Product reservedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(reservedProduct.getStock()).isZero();
        assertThat(reservedProduct.getReservedStock()).isEqualTo(100);

        log.info("=============== 구매확정 ===============");
        // 구매 확정
        placeOrderProductConcurrency(product.getId(), 100);

        // 구매 확정 결과 검증
        Product purchasedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertThat(purchasedProduct.getStock()).isZero();
        assertThat(purchasedProduct.getReservedStock()).isZero();
    }

    private void makeReservationConcurrency(Long productId, int threadCount) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    productService.makeReservation(productId, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }

    private void placeOrderProductConcurrency(Long productId, int threadCount) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    productService.placeOrder(productId, 1);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println(productRepository.findById(productId).orElseThrow().getReservedStock());
    }
}
