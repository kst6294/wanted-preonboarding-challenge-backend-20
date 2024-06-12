package org.example.preonboarding.stock.service;

import lombok.RequiredArgsConstructor;
import org.example.preonboarding.stock.entity.Stock;
import org.example.preonboarding.stock.exception.OutOfStockException;
import org.example.preonboarding.stock.repository.StockRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final TransactionTemplate transactionTemplate;

    public Stock getStockByProductNumber(String productNumber) {
        return stockRepository.findByProductNumber(productNumber);
    }

    @Override
    @Transactional
    public void deductStock(Long stockId, int quantity) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid stock Id:" + stockId));
        stock.deductQuantity(quantity);
        stockRepository.save(stock);
    }

}
