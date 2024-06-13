package com.example.hs.UnitTest.transactions;

import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.dto.TransactionRequest;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import java.util.Optional;

import static com.example.hs.global.exception.ErrorCode.ALREADY_PURCHASED;
import static com.example.hs.global.exception.ErrorCode.CAN_NOT_BUY_IF_SELLER;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_PRICE;
import static com.example.hs.global.exception.ErrorCode.NOT_SALES;
import static com.example.hs.global.exception.ErrorCode.SOLD_OUT_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionReserveUnitTest extends TransactionsBaseUnitTest{

  @DisplayName("상품 예약 성공 > 판매 가능 수량이 2개이고 1개만 예약 성공되었을 때")
  @Test
  public void testReserveTransactionsSuccess() {
    TransactionRequest request = new TransactionRequest(1L, 1000, 1);
    CustomUserDetails memberDetails = new CustomUserDetails(
        MemberUserDetailsDomain.fromEntity(mockBuyer1));

    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));
    when(memberRepository.findById(2L)).thenReturn(Optional.of(mockBuyer1));
    when(transactionRepository.existsByGoodsAndBuyer(mockGoods, mockBuyer1)).thenReturn(false);
    when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransactionByBuyer1);

    TransactionDto transactionDto = transactionService.reserveTransactions(request, memberDetails);

    assertNotNull(transactionDto);
    assertEquals(1L, transactionDto.getGoodsId());
    assertEquals(1, mockGoods.getReservedQuantity());
    assertEquals(GoodsStatus.SALE, mockGoods.getGoodsStatus());

    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(memberRepository, times(1)).findById(2L);
    verify(transactionRepository, times(1)).existsByGoodsAndBuyer(mockGoods, mockBuyer1);
    verify(transactionRepository, times(1)).save(any(Transaction.class));
  }

  @DisplayName("상품 예약 성공 > 판매 가능 수량이 2개이고 2개 모두 예약 성공되었을 때")
  @Test
  public void testReserveTransactionsSuccessTwoPeople() {
    TransactionRequest request = new TransactionRequest(1L, 1000, 1);
    CustomUserDetails memberDetails1 = new CustomUserDetails(
        MemberUserDetailsDomain.fromEntity(mockBuyer1));

    CustomUserDetails memberDetails2 = new CustomUserDetails(
        MemberUserDetailsDomain.fromEntity(mockBuyer2));

    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));
    when(memberRepository.findById(2L)).thenReturn(Optional.of(mockBuyer1));
    when(memberRepository.findById(3L)).thenReturn(Optional.of(mockBuyer2));
    when(transactionRepository.existsByGoodsAndBuyer(mockGoods, mockBuyer1)).thenReturn(false);
    when(transactionRepository.existsByGoodsAndBuyer(mockGoods, mockBuyer2)).thenReturn(false);
    when(transactionRepository.save(any(Transaction.class)))
        .thenReturn(mockTransactionByBuyer1)
        .thenReturn(mockTransactionByBuyer2);

    TransactionDto transactionDto1 = transactionService.reserveTransactions(request, memberDetails1);
    TransactionDto transactionDto2 = transactionService.reserveTransactions(request, memberDetails2);

    assertNotNull(transactionDto1);
    assertNotNull(transactionDto2);
    assertEquals(1L, transactionDto1.getGoodsId());
    assertEquals(1L, transactionDto2.getGoodsId());
    assertEquals(2, mockGoods.getReservedQuantity());
    assertEquals(GoodsStatus.RESERVED, mockGoods.getGoodsStatus());

    verify(goodsRepository, times(2)).findByIdWithLock(1L);
    verify(memberRepository, times(1)).findById(2L);
    verify(memberRepository, times(1)).findById(3L);
    verify(transactionRepository, times(1)).existsByGoodsAndBuyer(mockGoods, mockBuyer1);
    verify(transactionRepository, times(1)).existsByGoodsAndBuyer(mockGoods, mockBuyer2);
    verify(transactionRepository, times(2)).save(any(Transaction.class));
  }

  @DisplayName("상품 예약 실패 - 판매자가 자신 상품 예약 시도")
  @Test
  public void testReserveTransactionsFailIfSeller() {
    TransactionRequest request = new TransactionRequest(1L, 1000, 1);
    CustomUserDetails memberDetails = new CustomUserDetails(
        MemberUserDetailsDomain.fromEntity(mockSeller));

    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.reserveTransactions(request, memberDetails);
    });

    assertEquals(CAN_NOT_BUY_IF_SELLER, exception.getErrorCode());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(memberRepository, never()).findById(anyLong());
    verify(transactionRepository, never()).existsByGoodsAndBuyer(any(), any());
    verify(transactionRepository, never()).save(any(Transaction.class));
  }

  @DisplayName("상품 예약 실패 - 판매 완료된 상품")
  @Test
  public void testReserveTransactionsFailIfNotSale() {

    TransactionRequest request = new TransactionRequest(1L, 1000, 1);
    CustomUserDetails memberDetails = new CustomUserDetails(
        MemberUserDetailsDomain.fromEntity(mockBuyer1));

    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockSoldOutGoods));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.reserveTransactions(request, memberDetails);
    });

    assertEquals(NOT_SALES, exception.getErrorCode());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(memberRepository, never()).findById(anyLong());
    verify(transactionRepository, never()).existsByGoodsAndBuyer(any(), any());
    verify(transactionRepository, never()).save(any(Transaction.class));
  }

  @DisplayName("상품 예약 실패 - 가격 불일치")
  @Test
  public void testReserveTransactionsFailIfPriceMismatch() {
    TransactionRequest request = new TransactionRequest(1L, 500, 1);
    CustomUserDetails memberDetails = new CustomUserDetails(
        MemberUserDetailsDomain.fromEntity(mockBuyer1));

    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.reserveTransactions(request, memberDetails);
    });

    assertEquals(NOT_MATCH_PRICE, exception.getErrorCode());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(memberRepository, never()).findById(anyLong());
    verify(transactionRepository, never()).existsByGoodsAndBuyer(any(), any());
    verify(transactionRepository, never()).save(any(Transaction.class));
  }

  @DisplayName("상품 예약 실패 - 재고 부족")
  @Test
  public void testReserveTransactionsFailIfOutOfStock() {
    // 현재는 1개만 구매할 수 있도록 되어있지만 만약 request의 양이 재고보다 많을 경우
    TransactionRequest request = new TransactionRequest(1L, 1000, 3);
    CustomUserDetails memberDetails = new CustomUserDetails(
        MemberUserDetailsDomain.fromEntity(mockBuyer1));

    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.reserveTransactions(request, memberDetails);
    });

    assertEquals(SOLD_OUT_ERROR, exception.getErrorCode());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(memberRepository, never()).findById(anyLong());
    verify(transactionRepository, never()).existsByGoodsAndBuyer(any(), any());
    verify(transactionRepository, never()).save(any(Transaction.class));
  }

  @DisplayName("상품 예약 실패 - 이미 구매한 상품")
  @Test
  public void testReserveTransactionsFailIfAlreadyPurchased() {
    TransactionRequest request = new TransactionRequest(1L, 1000, 1);
    CustomUserDetails memberDetails = new CustomUserDetails(
        MemberUserDetailsDomain.fromEntity(mockBuyer1));

    when(goodsRepository.findByIdWithLock(1L)).thenReturn(Optional.of(mockGoods));
    when(memberRepository.findById(2L)).thenReturn(Optional.of(mockBuyer1));
    when(transactionRepository.existsByGoodsAndBuyer(mockGoods, mockBuyer1)).thenReturn(true);

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.reserveTransactions(request, memberDetails);
    });

    assertEquals(ALREADY_PURCHASED, exception.getErrorCode());
    verify(goodsRepository, times(1)).findByIdWithLock(1L);
    verify(memberRepository, times(1)).findById(2L);
    verify(transactionRepository, times(1)).existsByGoodsAndBuyer(mockGoods, mockBuyer1);
    verify(transactionRepository, never()).save(any(Transaction.class));
  }
}
