package com.example.hs.UnitTest.transactions;

import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.dto.ConfirmPurchaseRequest;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import com.example.hs.global.security.userdetails.MemberUserDetailsDomain;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.example.hs.domain.transaction.type.TransactionStatus.APPROVAL_OF_SALE;
import static com.example.hs.domain.transaction.type.TransactionStatus.CONFIRM_PURCHASE;
import static com.example.hs.global.exception.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionConfirmPurchaseUnitTest extends TransactionsBaseUnitTest {

  @DisplayName("구매 확정 성공 - 구매자 승인")
  @Test
  public void testConfirmPurchaseTransactionsSuccess() {
    ConfirmPurchaseRequest request = new ConfirmPurchaseRequest(1L, CONFIRM_PURCHASE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer1));

    mockTransactionByBuyer1.updateTransaction(APPROVAL_OF_SALE);
    when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransactionByBuyer1));
    when(memberRepository.findById(memberDetails.getId())).thenReturn(Optional.of(mockBuyer1));

    TransactionDto transactionDto = transactionService.confirmPurchaseTransactions(request, memberDetails);

    assertNotNull(transactionDto);
    assertEquals(CONFIRM_PURCHASE, transactionDto.getTransactionStatus());
    verify(transactionRepository, times(1)).findById(1L);
  }

  @DisplayName("구매 확정 실패 - 거래를 찾을 수 없음")
  @Test
  public void testConfirmPurchaseTransactionsFailIfNotFound() {
    ConfirmPurchaseRequest request = new ConfirmPurchaseRequest(1L, CONFIRM_PURCHASE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer1));

    when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.confirmPurchaseTransactions(request, memberDetails);
    });

    assertEquals(NOT_FOUND_TRANSACTION, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(1L);
  }

  @DisplayName("구매 확정 실패 - 구매자가 아님")
  @Test
  public void testConfirmPurchaseTransactionsFailIfNotBuyer() {
    Transaction approvalTransaction = Transaction.builder()
        .id(0L)
        .goods(mockGoods)
        .buyer(mockBuyer1)
        .transactionStatus(APPROVAL_OF_SALE)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    ConfirmPurchaseRequest request = new ConfirmPurchaseRequest(0L, CONFIRM_PURCHASE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer2));

    when(transactionRepository.findById(0L)).thenReturn(Optional.of(approvalTransaction));
    when(memberRepository.findById(memberDetails.getId())).thenReturn(Optional.of(mockBuyer2));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.confirmPurchaseTransactions(request, memberDetails);
    });

    assertEquals(NOT_MATCH_BUYER, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(0L);
  }

  @DisplayName("구매 확정 실패 - 승인되지 않은 거래1: 아직 예약 진행중")
  @Test
  public void testConfirmPurchaseTransactionsFailIfReserved() {
    ConfirmPurchaseRequest request = new ConfirmPurchaseRequest(1L, CONFIRM_PURCHASE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer1));

    when(transactionRepository.findById(1L)).thenReturn(Optional.of(mockTransactionByBuyer1));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.confirmPurchaseTransactions(request, memberDetails);
    });

    assertEquals(CAN_NOT_CONFIRM, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(1L);
  }

  @DisplayName("구매 확정 실패 - 승인되지 않은 거래2: 판매자에게 거절된 거래")
  @Test
  public void testConfirmPurchaseTransactionsFailIfRefusal() {
    ConfirmPurchaseRequest request = new ConfirmPurchaseRequest(5L, CONFIRM_PURCHASE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer2));

    when(transactionRepository.findById(5L)).thenReturn(Optional.of(mockTransactionInRefusalStatus));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.confirmPurchaseTransactions(request, memberDetails);
    });

    assertEquals(CAN_NOT_CONFIRM, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(5L);
  }

  @DisplayName("구매 확정 실패 - 이미 구매 확정된 거래")
  @Test
  public void testConfirmPurchaseTransactionsFailIfAlreadyConfirmed() {
    Transaction alreadyConfirmTransaction = Transaction.builder()
        .id(0L)
        .goods(mockGoods)
        .buyer(mockBuyer2)
        .transactionStatus(CONFIRM_PURCHASE)
        .priceAtPurchase(1000)
        .quantity(1)
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    ConfirmPurchaseRequest request = new ConfirmPurchaseRequest(0L, CONFIRM_PURCHASE);
    CustomUserDetails memberDetails = new CustomUserDetails(MemberUserDetailsDomain.fromEntity(mockBuyer2));

    when(transactionRepository.findById(0L)).thenReturn(Optional.of(alreadyConfirmTransaction));

    CustomException exception = assertThrows(CustomException.class, () -> {
      transactionService.confirmPurchaseTransactions(request, memberDetails);
    });

    assertEquals(CAN_NOT_CONFIRM, exception.getErrorCode());
    verify(transactionRepository, times(1)).findById(0L);
  }
}

