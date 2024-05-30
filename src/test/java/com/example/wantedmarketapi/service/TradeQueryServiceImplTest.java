package com.example.wantedmarketapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.dto.response.TradeResponseDto.*;
import com.example.wantedmarketapi.repository.TradeRepository;
import com.example.wantedmarketapi.service.impl.TradeQueryServiceImpl;
import com.example.wantedmarketapi.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TradeQueryServiceImplTest {

    @InjectMocks
    private TradeQueryServiceImpl tradeQueryService;

    @Mock
    private TradeRepository tradeRepository;

    @Test
    void testGetMyPurchaseProductList() {

        Member member = TestUtil.createMemberWithReflection();
        TestUtil.setField(member, "id", 1L);

        Product product = TestUtil.createProductWithReflection();
        TestUtil.setField(product, "id", 1L);
        TestUtil.setField(product, "productStatus", ProductStatus.DONE);

        Trade trade = TestUtil.createTradeWithReflection();
        TestUtil.setField(trade, "id", 1L);
        TestUtil.setField(trade, "purchaser", member);
        TestUtil.setField(trade, "product", product);

        when(tradeRepository.findAllByPurchaserIdAndProduct_ProductStatus(eq(1L), eq(ProductStatus.DONE))).thenReturn(Arrays.asList(trade));

        List<PurchaseProductListResponse> result = tradeQueryService.getMyPurchaseProductList(member);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

}
