package com.wanted.market.domain.trade.repository;

import com.wanted.market.domain.trade.dto.TradeDto;
import com.wanted.market.domain.trade.entity.Trade;
import com.wanted.market.global.common.code.TradeStatusCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    Optional<Trade> findByTradeNo(long tradeNo);

    Optional<Trade> findByProductProductNoAndBuyerMemberNoOrSellerMemberNo(long productNo, long buyerMemberNo, long sellerMemberNo);

    @Query(value = "SELECT new com.wanted.market.domain.trade.dto.TradeDto(t.tradeNo, t.product.productNo, t.product.name, t.status) FROM Trade t WHERE (t.buyer.memberNo=:memberNo OR t.seller.memberNo=:memberNo) AND t.product.productNo=:productNo")
    List<TradeDto> findAllByMemberNoAndProductNo(long memberNo, long productNo);

    @Query(value = "SELECT new com.wanted.market.domain.trade.dto.TradeDto(t.tradeNo, t.product.productNo, t.product.name, t.status) FROM Trade t WHERE t.buyer.memberNo=:buyerMemberNo AND t.status=:status")
    Page<TradeDto> findByBuyerNo(long buyerMemberNo, TradeStatusCode status, Pageable pageable);

    @Query(value = "SELECT new com.wanted.market.domain.trade.dto.TradeDto(t.tradeNo, t.product.productNo, t.product.name, t.status) FROM Trade t WHERE (t.buyer.memberNo=:memberNo OR t.seller.memberNo=:memberNo) AND t.status=:status")
    Page<TradeDto> findByMemberNoAndStatus(long memberNo, TradeStatusCode status, Pageable pageable);

    Optional<Trade> findByBuyerMemberNoAndTradeNoAndStatus(long buyerMemberNo, long tradeNo, TradeStatusCode status);

}
