package com.example.hs.domain.goods.service;

import static com.example.hs.global.exception.ErrorCode.INVALID_GOODS_STATUS_AT_FIRST;
import static com.example.hs.global.exception.ErrorCode.INVALID_MIN_QUANTITY_AT_FIRST;
import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_GOODS;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsRequest;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.repository.GoodsRepository;
import com.example.hs.domain.goods.type.GoodsStatus;
import com.example.hs.global.exception.CustomException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {
  private final GoodsRepository goodsRepository;

  public List<GoodsDto> getAll() {
    return GoodsDto.fromEntity(goodsRepository.findAll());
  }

  public GoodsDto getGoodsDetail(long goodsId) {
    return GoodsDto.fromEntity(goodsRepository.findById(goodsId)
        .orElseThrow(() -> new CustomException(NOT_FOUND_GOODS)));
  }

  public GoodsDto postGoods(GoodsRequest goodsRequest) {
    if (goodsRequest.getQuantity() == 0) {
      throw new CustomException(INVALID_MIN_QUANTITY_AT_FIRST);
    }

    if (goodsRequest.getGoodsStatus() != GoodsStatus.SALE) {
      throw new CustomException(INVALID_GOODS_STATUS_AT_FIRST);
    }

    Goods goods = goodsRepository.save(Goods.builder()
        .goodsName(goodsRequest.getGoodsName())
        .description(goodsRequest.getDescription())
        .price(goodsRequest.getPrice())
        .quantity(goodsRequest.getQuantity())
        .goodsStatus(goodsRequest.getGoodsStatus())
        .build());

    return GoodsDto.fromEntity(goods);
  }

}
