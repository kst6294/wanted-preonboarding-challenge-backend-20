package com.wanted.challenge.product.service;

import com.wanted.challenge.IntegrationTestSupport;
import com.wanted.challenge.MultiThreadTestProvider;
import com.wanted.challenge.MultiThreadTestProvider.Result;
import com.wanted.challenge.account.entity.Account;
import com.wanted.challenge.account.repository.AccountRepository;
import com.wanted.challenge.product.entity.Product;
import com.wanted.challenge.product.model.Price;
import com.wanted.challenge.product.model.Quantity;
import com.wanted.challenge.product.repository.ProductRepository;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProductMultiThreadTest extends IntegrationTestSupport {

    @Autowired
    ProductService productService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("한번에 여러명이 구매해도 수량이 정상적으로 차감되어야 한다")
    void purchaseMultiThread() throws Exception {

        // given
        int requestCount = 1_000;
        Account seller = accountRepository.save(new Account("seller", "1234"));

        Product product = productRepository.save(
                new Product(seller, "상품", new Price(10_000), new Quantity(requestCount)));

        CountDownLatch countDownLatch = new CountDownLatch(requestCount);
        Result result = new Result();

        // when
        IntStream.range(0, requestCount)
                .mapToObj(number -> accountRepository.save(new Account("buyer" + number, "1234")))
                .map(buyer -> new Thread(
                        new MultiThreadTestProvider<>(
                                countDownLatch, result,
                                new PurchaseMultiThreadRequest(product.getId(), buyer.getId()),
                                request -> productService.purchase(request.productId(), request.buyerId()))))
                .forEach(Thread::start);

        countDownLatch.await();

        // then
        Assertions.assertThat(result.getSuccessCount())
                .isEqualTo(requestCount);

        Product afterProduct = productRepository.findById(product.getId())
                .orElseThrow();

        Assertions.assertThat(afterProduct.getQuantity().value())
                .isZero();
    }

    record PurchaseMultiThreadRequest(Long productId, Long buyerId) {
    }

}
