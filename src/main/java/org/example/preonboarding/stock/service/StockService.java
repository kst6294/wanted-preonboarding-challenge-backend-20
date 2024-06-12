package org.example.preonboarding.stock.service;

import org.example.preonboarding.stock.entity.Stock;

public interface StockService {

    Stock getStockByProductNumber(String productNumber);

    void deductStock(Long stockId, int quantity);

}
