package com.example.wantedmarketapi.repository;

import com.example.wantedmarketapi.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
