package com.example.hs.domain.transaction.service;

import static com.example.hs.domain.transaction.type.TransactionStatus.APPROVAL_OF_SALE;
import static com.example.hs.domain.transaction.type.TransactionStatus.CANCEL_PURCHASE;
import static com.example.hs.domain.transaction.type.TransactionStatus.CONFIRM_PURCHASE;
import static com.example.hs.domain.transaction.type.TransactionStatus.REFUSAL_OF_SALE;
import static com.example.hs.domain.transaction.type.TransactionStatus.RESERVATION;
import static com.example.hs.global.exception.ErrorCode.ALREADY_CONFIRM_PURCHASE;
import static com.example.hs.global.exception.ErrorCode.ALREADY_PURCHASED;
import static com.example.hs.global.exception.ErrorCode.CAN_NOT_BUY_IF_SELLER;
import static com.example.hs.global.exception.ErrorCode.CAN_NOT_CONFIRM;
import static com.example.hs.global.exception.ErrorCode.NOT_CORRECT_TRANSACTION_STATUS;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_GOODS;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_TRANSACTION;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_BUYER;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_PRICE;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_SELLER;
import static com.example.hs.global.exception.ErrorCode.NOT_SALES;
import static com.example.hs.global.exception.ErrorCode.SOLD_OUT_ERROR;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.repository.GoodsRepository;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.domain.transaction.dto.ConfirmPurchaseRequest;
import com.example.hs.domain.transaction.dto.RefuseOrApproveRequest;
import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.dto.TransactionRequest;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.repository.TransactionRepository;
import com.example.hs.domain.transaction.type.TransactionStatus;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final GoodsRepository goodsRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public TransactionDto reserveTransactions(TransactionRequest request, CustomUserDetails member) {
    Goods goods = goodsRepository.findByIdWithLock(request.getGoodsId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_GOODS));
    if (goods.getSeller().getId() == member.getId()) {
      throw new CustomException(CAN_NOT_BUY_IF_SELLER);
    }

    if (goods.getGoodsStatus() != GoodsStatus.SALE
        || goods.getTotalTransactionQuantity() == 0) {
      throw new CustomException(NOT_SALES);
    }

    if (goods.getPrice() != request.getPrice()) {
      throw new CustomException(NOT_MATCH_PRICE);
    }

    int remainGoods = goods.getTotalTransactionQuantity()
        - (goods.getReservedQuantity() + request.getQuantity());
    if (remainGoods < 0) {
      throw new CustomException(SOLD_OUT_ERROR);
    }

    Member buyer = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
    if (transactionRepository.existsByGoodsAndBuyer(goods, buyer)) {
      throw new CustomException(ALREADY_PURCHASED);
    }

    goods.purchaseByBuyer(request.getQuantity());

    Transaction transaction = Transaction.builder()
        .goods(goods)
        .buyer(buyer)
        .transactionStatus(TransactionStatus.RESERVATION)
        .priceAtPurchase(request.getPrice())
        .quantity(request.getQuantity())
        .transactionCompleteDateTime(LocalDateTime.now())
        .build();

    return TransactionDto.fromEntity(transactionRepository.save(transaction));
  }

  @Transactional
  public TransactionDto refuseOrApproveTransactions(RefuseOrApproveRequest request, CustomUserDetails member) {
    Transaction transaction = transactionRepository.findById(request.getTransactionId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_TRANSACTION));

    if (transaction.getGoods().getSeller().getId() != member.getId()) {
      throw new CustomException(NOT_MATCH_SELLER);
    }
    if (transaction.getTransactionStatus() != RESERVATION) {
      throw new CustomException(NOT_CORRECT_TRANSACTION_STATUS);
    }

    if (transaction.getTransactionStatus() == CONFIRM_PURCHASE) {
      throw new CustomException(ALREADY_CONFIRM_PURCHASE);
    }

    Goods goods = transaction.getGoods();
    if (request.getTransactionStatus() == REFUSAL_OF_SALE) {
      goods.refusalOfSaleBySeller(transaction.getQuantity());
    }
    if (request.getTransactionStatus() == APPROVAL_OF_SALE) {
      goods.approvalOfSaleBySeller(transaction.getQuantity());
    }

    transaction.updateTransaction(request.getTransactionStatus());

    return TransactionDto.fromEntity(transaction);
  }

  @Transactional
  public TransactionDto confirmPurchaseTransactions(ConfirmPurchaseRequest request, CustomUserDetails member) {
    Transaction transaction = transactionRepository.findById(request.getTransactionId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_TRANSACTION));

    if (transaction.getBuyer().getId() != member.getId()) {
      throw new CustomException(NOT_MATCH_BUYER);
    }

    if (transaction.getTransactionStatus() != APPROVAL_OF_SALE) {
      throw new CustomException(CAN_NOT_CONFIRM);
    }

    if (transaction.getTransactionStatus() == CONFIRM_PURCHASE
        || transaction.getTransactionStatus() == CANCEL_PURCHASE) {
      throw new CustomException(ALREADY_CONFIRM_PURCHASE);
    }

    Goods goods = transaction.getGoods();
    if (request.getTransactionStatus() == CANCEL_PURCHASE) {
      goods.cancelPurchaseByBuyer(transaction.getQuantity());
    }

    if (request.getTransactionStatus() == CONFIRM_PURCHASE) {
      goods.confirmPurchaseByBuyer(transaction.getQuantity());
    }
    transaction.updateTransaction(request.getTransactionStatus());

    return TransactionDto.fromEntity(transaction);
  }
}
