package com.example.hs.domain.buyer.service;

import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.repository.TransactionRepository;
import com.example.hs.domain.transaction.type.TransactionStatus;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.exception.ErrorCode;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyerService {
  private final TransactionRepository transactionRepository;
  private final MemberRepository memberRepository;

  public List<GoodsDto> getAllMyTransactionGoods(CustomUserDetails member) {
    Member buyer = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

    List<Transaction> transactionsByBuyer = transactionRepository.findAllByBuyer(buyer);
    return transactionsByBuyer.stream()
        .map(transaction -> GoodsDto.fromEntity(transaction.getGoods()))
        .collect(Collectors.toList());
  }

  public List<GoodsDto> getAllMyTransactionGoodsByStatus(CustomUserDetails member, TransactionStatus transactionStatus) {
    Member buyer = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

    List<Transaction> transactionsByBuyer = transactionRepository.findAllByBuyerAndTransactionStatus(
        buyer, transactionStatus);

    return transactionsByBuyer.stream()
        .map(transaction -> GoodsDto.fromEntity(transaction.getGoods()))
        .collect(Collectors.toList());
  }
}
