package com.example.hs.domain.transaction.service;

import static com.example.hs.global.exception.ErrorCode.ALREADY_PURCHASED;
import static com.example.hs.global.exception.ErrorCode.CAN_NOT_BUY_IF_SELLER;
import static com.example.hs.global.exception.ErrorCode.CHECK_IF_SOLD_OUT_GOODS;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_GOODS;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_PRICE;
import static com.example.hs.global.exception.ErrorCode.NOT_SALES;
import static com.example.hs.global.exception.ErrorCode.SOLD_OUT_ERROR;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.repository.GoodsRepository;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.dto.TransactionRequest;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.repository.TransactionRepository;
import com.example.hs.domain.transaction.type.TransactionStatus;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.exception.ErrorCode;
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
        || goods.getQuantity() == 0) {
      throw new CustomException(NOT_SALES);
    }

    if (goods.getPrice() != request.getPrice()) {
      throw new CustomException(NOT_MATCH_PRICE);
    }

    int remainGoods = goods.getQuantity() - request.getQuantity();
    if (remainGoods < 0) {
      throw new CustomException(SOLD_OUT_ERROR);
    }

    Member buyer = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));
    if (transactionRepository.existsByGoodsAndBuyer(goods, buyer)) {
      throw new CustomException(ALREADY_PURCHASED);
    }

    if (remainGoods == 0) {
      goods.setGoodsStatus(GoodsStatus.RESERVED);
    }
    goods.setQuantity(remainGoods);

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
}
