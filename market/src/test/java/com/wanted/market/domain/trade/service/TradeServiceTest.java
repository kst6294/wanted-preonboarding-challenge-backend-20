package com.wanted.market.domain.trade.service;

import com.wanted.market.domain.member.entity.Member;
import com.wanted.market.domain.member.repository.MemberRepository;
import com.wanted.market.domain.product.entity.Product;
import com.wanted.market.domain.product.repository.ProductRepository;
import com.wanted.market.domain.trade.entity.Trade;
import com.wanted.market.domain.trade.repository.TradeRepository;
import com.wanted.market.domain.trade.request.TradeRequest;
import com.wanted.market.global.common.code.BaseStatusCode;
import com.wanted.market.global.common.code.ProductStatusCode;
import com.wanted.market.global.common.code.RoleCode;
import com.wanted.market.global.common.code.TradeStatusCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class TradeServiceTest {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
    }

    private Product createProduct(Member seller) {
        Product product = Product.builder()
                .name("테스트제품")
                .seller(seller)
                .price(10000)
                .description("테스트 제품입니다")
                .build();

        productRepository.save(product);

        return product;
    }

    private Member createMember(final String id) {
        Member member = Member.builder()
                .memberId(id)
                .name("테스트")
                .password(passwordEncoder.encode("password"))
                .role(RoleCode.MEMBER)
                .status(BaseStatusCode.ACTIVE)
                .buyerTrades(new ArrayList<>())
                .sellerTrades(new ArrayList<>())
                .build();

        memberRepository.save(member);

        return member;
    }

    private Trade createTrade(Product product, Member seller, Member buyer) {
        Trade trade = Trade.builder()
                .product(product)
                .seller(seller)
                .buyer(buyer)
                .status(TradeStatusCode.REQUEST)
                .build();

        tradeRepository.save(trade);

        return trade;
    }

    private HttpServletRequest getHttpServletRequest(Member member) {
        HttpServletRequest httpRequest = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(httpRequest.getSession()).thenReturn(session);
        when(session.getAttribute("memberNo")).thenReturn(member.getMemberNo());
        return httpRequest;
    }

    @Test
    @DisplayName("product, trade 상태값테스트")
    void 거래중() {

        //given
        Member buyer = createMember("buyer");
        Member seller = createMember("seller");
        Product product = createProduct(seller);

        HttpServletRequest httpRequest = getHttpServletRequest(buyer);

        //when
        long result = tradeService.requestTrade(new TradeRequest(product.getProductNo(), 0));// 0 은 쓰레기 값
        Trade trade = tradeService.getTrade(product.getProductNo());

        TradeStatusCode tradeStatus = trade.getStatus();
        ProductStatusCode productStatus = product.getStatus();

        //then
        Assertions.assertEquals(tradeStatus, TradeStatusCode.REQUEST);
        Assertions.assertEquals(productStatus, ProductStatusCode.RESERVE);
    }

    @Test
    @DisplayName("product, trade 상태값테스트")
    void 거래완료() {

        //given
        Member buyer = createMember("buyer");
        Member seller = createMember("seller");
        Product product = createProduct(seller);

        long tradeNo = createTrade(product, seller, buyer).getTradeNo();

        HttpServletRequest httpRequest = getHttpServletRequest(seller);
        tradeService.acceptTrade(new TradeRequest(product.getProductNo(), tradeNo));

        //when
        Trade trade = tradeService.getTrade(product.getProductNo());

        TradeStatusCode tradeStatus = trade.getStatus();
        ProductStatusCode productStatus = product.getStatus();

        //then
        Assertions.assertEquals(tradeStatus, TradeStatusCode.COMPLETE);
        Assertions.assertEquals(productStatus, ProductStatusCode.COMPLETE);
    }
}
