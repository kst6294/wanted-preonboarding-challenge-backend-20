package com.example.hs.domain.seller.service;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsDtoForSeller;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.repository.GoodsRepository;
import com.example.hs.domain.goods.type.GoodsStatus;
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

  @Transactional(readOnly = true)
  public List<GoodsDto> getAllMyGoods(CustomUserDetails member) {
    Member seller = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    List<Goods> sellerGoods = goodsRepository.findAllBySeller(seller);
    return GoodsDtoForSeller.fromEntity(sellerGoods);
  }

  @Transactional(readOnly = true)
  public List<GoodsDto> getAllMyGoodsInStatus(CustomUserDetails member, GoodsStatus status) {
    Member seller = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    List<Goods> sellerGoods = goodsRepository.findAllBySellerAndGoodsStatus(seller, status);
    return GoodsDtoForSeller.fromEntity(sellerGoods);
  }

}
