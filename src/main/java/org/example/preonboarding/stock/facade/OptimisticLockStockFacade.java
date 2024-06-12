package org.example.preonboarding.stock.facade;

import lombok.RequiredArgsConstructor;
import org.example.preonboarding.stock.service.OptimisticLockStockService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public void deductStock(Long id, int quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticLockStockService.deductStock(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
