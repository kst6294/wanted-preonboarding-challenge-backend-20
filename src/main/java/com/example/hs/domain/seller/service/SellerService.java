package com.example.hs.domain.seller.service;

import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_GOODS;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_SELLER;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsDtoForSeller;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.repository.GoodsRepository;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.repository.TransactionRepository;
import com.example.hs.domain.transaction.type.TransactionStatus;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.exception.ErrorCode;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerService {
  private final GoodsRepository goodsRepository;
  private final MemberRepository memberRepository;
  private final TransactionRepository transactionRepository;

  @Transactional(readOnly = true)
  public List<GoodsDto> getAllMyGoods(CustomUserDetails member) {
    Member seller = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    List<Goods> sellerGoods = goodsRepository.findAllBySeller(seller);
    return GoodsDtoForSeller.fromEntity(sellerGoods);
  }

  @Transactional(readOnly = true)
  public List<GoodsDto> getAllMyGoodsInStatus(CustomUserDetails member, GoodsStatus goodsStatus) {
    Member seller = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    List<Goods> sellerGoods = goodsRepository.findAllBySellerAndGoodsStatus(seller, goodsStatus);
    return GoodsDtoForSeller.fromEntity(sellerGoods);
  }

  @Transactional(readOnly = true)
  public List<TransactionDto> getMyGoodsTransactions(CustomUserDetails member, long goodsId) {
    Goods goods = goodsRepository.findByIdWithLock(goodsId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_GOODS));
    if (member.getId() != goods.getSeller().getId()) {
      throw new CustomException(NOT_MATCH_SELLER);
    }
    List<Transaction> transactions = transactionRepository.findAllByGoods(goods);
    return TransactionDto.fromEntity(transactions);
  }

  @Transactional(readOnly = true)
  public List<TransactionDto> getMyGoodsTransactionsByStatus(CustomUserDetails member, long goodsId, TransactionStatus transactionStatus) {
    Goods goods = goodsRepository.findByIdWithLock(goodsId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_GOODS));
    if (member.getId() != goods.getSeller().getId()) {
      throw new CustomException(NOT_MATCH_SELLER);
    }
    List<Transaction> transactions = transactionRepository.findAllByGoodsAndTransactionStatus(goods, transactionStatus);
    return TransactionDto.fromEntity(transactions);
  }

}
