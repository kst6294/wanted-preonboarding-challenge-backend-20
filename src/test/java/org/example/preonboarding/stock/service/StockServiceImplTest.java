package org.example.preonboarding.stock.service;

import org.example.preonboarding.IntegrationTestSupport;
import org.example.preonboarding.stock.entity.Stock;
import org.example.preonboarding.stock.facade.OptimisticLockStockFacade;
import org.example.preonboarding.stock.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class StockServiceImplTest extends IntegrationTestSupport {

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @DisplayName("생성된 재고에 재고1개를 차감하는 요청 세 개를 동시에 낙관적락 테스트")
    @Test
    void test() {
        // given
        Stock stock1 = Stock.create("001", 1);
        Stock savedStock = stockRepository.save(stock1);
        int threadCount = 3;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        // when
        Future<?> future = executorService.submit(
                () -> stockService.deductStock(savedStock.getId(), 1));
        Future<?> future2 = executorService.submit(
                () -> stockService.deductStock(savedStock.getId(), 1));
        Future<?> future3 = executorService.submit(
                () -> stockService.deductStock(savedStock.getId(), 1));

        Exception result = new Exception();

        try {
            future.get();
            future2.get();
            future3.get();
        } catch (ExecutionException | InterruptedException e) {
            result = (Exception) e.getCause();
        }

        // then
        assertInstanceOf(OptimisticLockingFailureException.class, result);
    }

}