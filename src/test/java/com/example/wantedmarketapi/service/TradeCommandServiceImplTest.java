package com.example.wantedmarketapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.wantedmarketapi.domain.Product;
import com.example.wantedmarketapi.domain.Trade;
import com.example.wantedmarketapi.domain.enums.ProductStatus;
import com.example.wantedmarketapi.domain.member.Member;
import com.example.wantedmarketapi.exception.custom.ProductException;
import com.example.wantedmarketapi.repository.ProductRepository;
import com.example.wantedmarketapi.repository.TradeRepository;
import com.example.wantedmarketapi.service.impl.TradeCommandServiceImpl;
import com.example.wantedmarketapi.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TradeCommandServiceImplTest {

    @InjectMocks
    private TradeCommandServiceImpl tradeCommandService;

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void createTrade_ValidProduct_ShouldSucceed() {
        // Given
        Long productId = 1L;
        Long memberId = 1L;
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Member member = TestUtil.createMemberWithReflection();
        TestUtil.setField(member, "id", memberId);

        Product product = TestUtil.createProductWithReflection();
        TestUtil.setField(product, "id", productId);
        TestUtil.setField(product, "seller", member);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(tradeRepository.save(any(Trade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Trade result = tradeCommandService.createTrade(member, productId);

        // Then
        assertNotNull(result);
        assertEquals(product, result.getProduct());
        assertEquals(member, result.getPurchaser());
        assertEquals(ProductStatus.RESERVATION, product.getProductStatus());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(product);
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    public void createTrade_ProductNotFound_ShouldThrowException() {
        // Given
        Long productId = 1L;
        Long memberId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Member member = TestUtil.createMemberWithReflection();
        TestUtil.setField(member, "id", memberId);

        // When & Then
        assertThrows(ProductException.class, () -> tradeCommandService.createTrade(member, productId));

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
        verify(tradeRepository, never()).save(any(Trade.class));
    }
}