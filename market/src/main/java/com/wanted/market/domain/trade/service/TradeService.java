package com.wanted.market.domain.trade.service;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.domain.member.service.MemberService;
import com.wanted.market.domain.product.entity.Product;
import com.wanted.market.domain.product.service.ProductService;
import com.wanted.market.domain.trade.dto.TradeDto;
import com.wanted.market.domain.trade.entity.Trade;
import com.wanted.market.domain.trade.repository.TradeRepository;
import com.wanted.market.domain.trade.request.TradeRequest;
import com.wanted.market.global.auth.service.SessionUtils;
import com.wanted.market.global.common.code.TradeStatusCode;
import com.wanted.market.global.common.exception.DataNotFoundException;
import com.wanted.market.global.common.exception.NoPermissionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    private final MemberService memberService;
    private final ProductService productService;
    private final SessionUtils sessionUtils;

    @Transactional(rollbackFor = Exception.class)
    public long requestTrade(TradeRequest request) {
        Member member = memberService.getMember(sessionUtils.getMemberNo());
        Product product = productService.getProduct(request.getProductNo());

        if(product.getSeller().getMemberNo() == sessionUtils.getMemberNo()) {
            throw new NoPermissionException();
        }

        if (! product.isAvailable()) {
            throw new DataNotFoundException();
        }

        Trade trade = new Trade(product, member);
        product.changeStatus();
        tradeRepository.save(trade);

        return trade.getTradeNo();
    }

    @Transactional(rollbackFor = Exception.class)
    public long acceptTrade(TradeRequest request) {
        Product product = productService.getProduct(request.getProductNo());

        if(product.getSeller().getMemberNo() != sessionUtils.getMemberNo()) {
            throw new NoPermissionException();
        }

        Trade trade = tradeRepository.findByTradeNo(request.getTradeNo())
                .orElseThrow(() -> new DataNotFoundException());

        trade.changeStatus(TradeStatusCode.ACCEPT);
        product.changeStatus();

        return trade.getTradeNo();
    }

    @Transactional(rollbackFor = Exception.class)
    public long completeTrade(TradeRequest request) {
        Trade trade = tradeRepository.findByBuyerMemberNoAndTradeNoAndStatus(sessionUtils.getMemberNo(), request.getTradeNo(), TradeStatusCode.ACCEPT)
                .orElseThrow(() -> new DataNotFoundException());

        trade.changeStatus(TradeStatusCode.COMPLETE);
        trade.getProduct().changeStatus();

        return trade.getTradeNo();
    }

    public List<TradeDto> getTrades(final long productNo) {
        return tradeRepository.findAllByMemberNoAndProductNo(sessionUtils.getMemberNo(), productNo);
    }

    public List<TradeDto> getPurchasedTrades(final int page) {
        return tradeRepository.findByBuyerNo(sessionUtils.getMemberNo(), PageRequest.of(page, 10)).toList();
    }

    public List<TradeDto> getReservedTrades(final int page) {
        return tradeRepository.findByMemberNoAndStatus(sessionUtils.getMemberNo(), TradeStatusCode.REQUEST, PageRequest.of(page, 10)).toList();
    }

    public Trade getTrade(long productNo) {
        long memberNo = sessionUtils.getMemberNo();
        return tradeRepository.findByProductProductNoAndBuyerMemberNoOrSellerMemberNo(productNo, memberNo, memberNo)
                .orElseThrow(() -> new DataNotFoundException());
    }
}
