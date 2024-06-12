package org.example.preonboarding.stock.service;


import lombok.RequiredArgsConstructor;
import org.example.preonboarding.stock.entity.Stock;
import org.example.preonboarding.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void deductStock(Long id, int quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.deductQuantity(quantity);

        stockRepository.save(stock);
    }
}
