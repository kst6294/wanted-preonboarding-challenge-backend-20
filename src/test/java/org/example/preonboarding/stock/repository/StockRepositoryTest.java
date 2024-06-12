package org.example.preonboarding.stock.repository;

import org.example.preonboarding.DataJPATestSupport;
import org.example.preonboarding.stock.entity.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StockRepositoryTest extends DataJPATestSupport {

    @DisplayName("상품번호 리스트로 재고를 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // given
        String exampleProductNumber = productNumberFactory.createNextProductNumber();
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // when
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 1),
                        tuple("002", 2)
                );
    }

    @DisplayName("상품번호로 재고를 조회한다.")
    @Test
    void findByProductNumber() {
        // given
        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        Stock stock3 = Stock.create("003", 3);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // when
        Stock stock = stockRepository.findByProductNumber("001");

        // then
        assertThat(stock)
                .extracting("productNumber", "quantity")
                .contains("001", 1);
    }

    @DisplayName("재고를 감소시킨다.")
    @Test
    void deductQuantity() {
        // given
        Stock stock1 = Stock.create("001", 100);
        Stock stock2 = Stock.create("002", 200);
        Stock stock3 = Stock.create("003", 300);
        stockRepository.saveAll(List.of(stock1, stock2, stock3));

        // when
        Stock stock = stockRepository.findByProductNumber("001");
        stock.deductQuantity(2);
        // then


        assertThat(stock)
                .extracting("productNumber", "quantity")
                .contains("001", 98);
    }

}