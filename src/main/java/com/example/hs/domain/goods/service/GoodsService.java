package com.example.hs.domain.goods.service;

import static com.example.hs.domain.goods.type.GoodsStatus.SOLD_OUT;
import static com.example.hs.global.exception.ErrorCode.CHECK_GOODS_PRICE_ZERO;
import static com.example.hs.global.exception.ErrorCode.CHECK_IF_SOLD_OUT_GOODS;
import static com.example.hs.global.exception.ErrorCode.INVALID_GOODS_STATUS_AT_FIRST;
import static com.example.hs.global.exception.ErrorCode.INVALID_MIN_QUANTITY_AT_FIRST;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_GOODS;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_MEMBER;
import static com.example.hs.global.exception.ErrorCode.NOT_MATCH_SELLER;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.repository.MemberRepository;
import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsEditRequest;
import com.example.hs.domain.goods.dto.GoodsRequest;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.repository.GoodsRepository;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.global.exception.CustomException;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodsService {
  private final GoodsRepository goodsRepository;
  private final MemberRepository memberRepository;

  public List<GoodsDto> getAll() {
    return GoodsDto.fromEntity(goodsRepository.findAll());
  }

  public GoodsDto getGoodsDetail(long goodsId) {
    return GoodsDto.fromEntity(goodsRepository.findById(goodsId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_GOODS)));
  }

  @Transactional
  public GoodsDto postGoods(GoodsRequest goodsRequest, CustomUserDetails member) {
    if (goodsRequest.getQuantity() == 0) {
      throw new CustomException(INVALID_MIN_QUANTITY_AT_FIRST);
    }

    if (goodsRequest.getGoodsStatus() != GoodsStatus.SALE) {
      throw new CustomException(INVALID_GOODS_STATUS_AT_FIRST);
    }

    Member seller = memberRepository.findById(member.getId())
        .orElseThrow(() -> new CustomException(NOT_FOUND_MEMBER));

    Goods goods = goodsRepository.save(Goods.builder()
        .goodsName(goodsRequest.getGoodsName())
        .description(goodsRequest.getDescription())
        .price(goodsRequest.getPrice())
        .quantity(goodsRequest.getQuantity())
        .goodsStatus(goodsRequest.getGoodsStatus())
        .seller(seller)
        .build());

    return GoodsDto.fromEntity(goods);
  }

  @Transactional
  public GoodsDto updateGoods(long goodsId,
      GoodsEditRequest goodsEditRequest, CustomUserDetails member) {
    Goods goods = goodsRepository.findById(goodsId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_GOODS));

    if (member.getId() != goods.getSeller().getId()) {
      throw new CustomException(NOT_MATCH_SELLER);
    }

    if ((goodsEditRequest.getQuantity() == 0 && goodsEditRequest.getGoodsStatus() != SOLD_OUT)
        || (goodsEditRequest.getGoodsStatus() == SOLD_OUT && goodsEditRequest.getQuantity() != 0)) {
      throw new CustomException(CHECK_IF_SOLD_OUT_GOODS);
    }

    if (goodsEditRequest.getPrice() == 0) {
      throw new CustomException(CHECK_GOODS_PRICE_ZERO);
    }

    goods.updateGoods(goodsEditRequest);

    return GoodsDto.fromEntity(goods);
  }

  @Transactional
  public String deleteGoods(long goodsId, CustomUserDetails member) {
    Goods goods = goodsRepository.findById(goodsId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_GOODS));

    if (member.getId() != goods.getSeller().getId()) {
      throw new CustomException(NOT_MATCH_SELLER);
    }

    goodsRepository.delete(goods);
    return String.format("%s 상품이 삭제되었습니다.", goods.getGoodsName());
  }
}
