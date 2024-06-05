package com.example.hs.domain.goods.service;

import static com.example.hs.global.exception.ErrorCode.NOT_FOUND_GOODS;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.repository.GoodsRepository;
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
}
