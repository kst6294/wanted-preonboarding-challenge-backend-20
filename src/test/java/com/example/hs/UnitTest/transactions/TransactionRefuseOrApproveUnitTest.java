package com.example.hs.UnitTest.transactions;

import static com.example.hs.domain.transaction.type.TransactionStatus.APPROVAL_OF_SALE;
import static com.example.hs.domain.transaction.type.TransactionStatus.CANCEL_PURCHASE;
import static com.example.hs.domain.transaction.type.TransactionStatus.REFUSAL_OF_SALE;
import static com.example.hs.global.exception.ErrorCode.NOT_CORRECT_TRANSACTION_STATUS;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_TRANSACTION;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_SELLER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.domain.transaction.dto.RefuseOrApproveRequest;
import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionRefuseOrApproveUnitTest extends TransactionsBaseUnitTest {

  @DisplayName("거래 거절 성공 - 판매자가 거절1: 판매 중인 상품일때")
  @Test
  public void testRefuseTransactionsSuccessIfSaleGoods() {
    RefuseOrApproveRequest request = new RefuseOrApproveRequest(1L, REFUSAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransactionByBuyer1));
    when(memberRepository.findById(memberDetails.getId())).thenReturn(Optional.of(mockSeller));

    mockTransactionByBuyer1.getGoods().purchaseByBuyer(mockTransactionByBuyer1.getQuantity());
    TransactionDto transactionDto = transactionService.refuseOrApproveTransactions(request, memberDetails);

    assertNotNull(transactionDto);
    assertEquals(REFUSAL_OF_SALE, transactionDto.getTransactionStatus());
    assertEquals(0, mockTransactionByBuyer1.getGoods().getReservedQuantity());
    assertEquals(GoodsStatus.SALE, mockTransactionByBuyer1.getGoods().getGoodsStatus());
    verify(transactionRepository, times(1)).findById(1L);
  }

  @DisplayName("거래 거절 성공 - 판매자가 거절2: 예약 상태인 상품 일때")
  @Test
  public void testRefuseTransactionsSuccessIfReservedGoods() {
    RefuseOrApproveRequest request = new RefuseOrApproveRequest(3L, REFUSAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(transactionRepository.findById(3L)).thenReturn(Optional.of(mockTransactionInReservedGoodsStatus));
    when(memberRepository.findById(memberDetails.getId())).thenReturn(Optional.of(mockSeller));

    assertEquals(GoodsStatus.RESERVED, mockTransactionInReservedGoodsStatus.getGoods().getGoodsStatus());
    mockTransactionInReservedGoodsStatus.getGoods().purchaseByBuyer(mockTransactionInReservedGoodsStatus.getQuantity());
    TransactionDto transactionDto = transactionService.refuseOrApproveTransactions(request, memberDetails);

    assertNotNull(transactionDto);
    assertEquals(REFUSAL_OF_SALE, transactionDto.getTransactionStatus());
    assertEquals(0, mockTransactionByBuyer1.getGoods().getReservedQuantity());
    assertEquals(GoodsStatus.SALE, mockTransactionInReservedGoodsStatus.getGoods().getGoodsStatus());
    verify(transactionRepository, times(1)).findById(3L);
  }

  @DisplayName("거래 승인 성공 - 판매자가 승인")
  @Test
  public void testApproveTransactionsSuccess() {
    RefuseOrApproveRequest request = new RefuseOrApproveRequest(1L, APPROVAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransactionByBuyer1));
    when(memberRepository.findById(memberDetails.getId())).thenReturn(Optional.of(mockSeller));

    mockTransactionByBuyer1.getGoods().purchaseByBuyer(mockTransactionByBuyer1.getQuantity());
    TransactionDto transactionDto = transactionService.refuseOrApproveTransactions(request, memberDetails);

    assertNotNull(transactionDto);
    assertEquals(APPROVAL_OF_SALE, transactionDto.getTransactionStatus());
    assertEquals(0, mockTransactionByBuyer1.getGoods().getReservedQuantity());
    verify(transactionRepository, times(1)).findById(1L);
  }

  @DisplayName("거래 승인/거절 실패 - 거래를 찾을 수 없음")
  @Test
  public void testRefuseOrApproveTransactionsFailIfNotFound() {
    RefuseOrApproveRequest request = new RefuseOrApproveRequest(1L, APPROVAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.refuseOrApproveTransactions(request, memberDetails);
    });

    assertEquals(NOT_FOUND_TRANSACTION, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(1L);
  }

  @DisplayName("거래 승인/거절 실패 - 판매자가 아님")
  @Test
  public void testRefuseOrApproveTransactionsFailIfNotSeller() {
    RefuseOrApproveRequest request = new RefuseOrApproveRequest(1L, APPROVAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer1));

    when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransactionByBuyer1));
    when(memberRepository.findById(memberDetails.getId())).thenReturn(Optional.of(mockBuyer1));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.refuseOrApproveTransactions(request, memberDetails);
    });

    assertEquals(NOT_MATCH_SELLER, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(1L);
  }

  @DisplayName("거래 승인/거절 실패 - 잘못된 거래 상태1: 이미 거절된 상태")
  @Test
  public void testRefuseOrApproveTransactionsFailIfAlreadyRefusalStatus() {
    RefuseOrApproveRequest request = new RefuseOrApproveRequest(5L, APPROVAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(transactionRepository.findById(5L)).thenReturn(Optional.of(mockTransactionInRefusalStatus));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.refuseOrApproveTransactions(request, memberDetails);
    });

    assertEquals(NOT_CORRECT_TRANSACTION_STATUS, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(5L);
  }

  @DisplayName("거래 승인/거절 실패 - 잘못된 거래 상태2: 이미 승인된 상태")
  @Test
  public void testRefuseOrApproveTransactionsFailIfAlreadyApprovalStatus() {
    Transaction alreadyApprovalTransaction = Transaction.builder()
        .id(0L)
        .goods(mockGoods)
        .buyer(mockBuyer1)
        .transactionStatus(APPROVAL_OF_SALE)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    RefuseOrApproveRequest request = new RefuseOrApproveRequest(0L, APPROVAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(transactionRepository.findById(0L)).thenReturn(Optional.of(alreadyApprovalTransaction));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.refuseOrApproveTransactions(request, memberDetails);
    });

    assertEquals(NOT_CORRECT_TRANSACTION_STATUS, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(0L);
  }

  @DisplayName("거래 승인/거절 실패 - 잘못된 거래 상태3: 이미 구매 확정된 상태")
  @Test
  public void testRefuseOrApproveTransactionsFailIfAlreadyConfirmStatus() {

    RefuseOrApproveRequest request = new RefuseOrApproveRequest(4L, APPROVAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(transactionRepository.findById(4L)).thenReturn(Optional.of(mockTransactionInConfirmPurchaseStatus));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.refuseOrApproveTransactions(request, memberDetails);
    });

    assertEquals(NOT_CORRECT_TRANSACTION_STATUS, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(4L);
  }

  @DisplayName("거래 승인/거절 실패 - 잘못된 거래 상태4: 이미 구매 취소된 상태")
  @Test
  public void testRefuseOrApproveTransactionsFailIfAlreadyCancelStatus() {
    Transaction alreadyCancelTransaction = Transaction.builder()
        .id(0L)
        .goods(mockGoods)
        .buyer(mockBuyer1)
        .transactionStatus(CANCEL_PURCHASE)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    RefuseOrApproveRequest request = new RefuseOrApproveRequest(0L, APPROVAL_OF_SALE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockSeller));

    when(transactionRepository.findById(0L)).thenReturn(Optional.of(alreadyCancelTransaction));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.refuseOrApproveTransactions(request, memberDetails);
    });

    assertEquals(NOT_CORRECT_TRANSACTION_STATUS, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(0L);
  }
}

