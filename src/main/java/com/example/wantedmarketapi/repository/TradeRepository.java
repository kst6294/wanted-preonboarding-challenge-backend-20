package com.example.wantedmarketapi.repository;

import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findAllByPurchaserIdAndProduct_ProductStatus(Long purchaserId, ProductStatus productStatus);

    List<Trade> findAllByPurchaserIdOrSellerIdAndProduct_ProductStatus(Long purchaserId, Long sellerId, ProductStatus productStatus);

}
