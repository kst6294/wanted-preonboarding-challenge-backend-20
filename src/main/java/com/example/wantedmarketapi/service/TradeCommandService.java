package com.example.wantedmarketapi.service;

import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.member.Member;

public interface TradeCommandService {

    Trade createTrade(Member member, Long productId);

}
