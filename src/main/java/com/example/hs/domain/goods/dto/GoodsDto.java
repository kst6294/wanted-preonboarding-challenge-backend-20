package com.example.hs.domain.goods.dto;

import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.type.GoodsStatus;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDto {
  private long id;
  private String goodsName;
  private String description;
  private int price;
  private int availableQuantity;
  private GoodsStatus goodsStatus;
  private long sellerId;


  public static List<GoodsDto> fromEntity(List<Goods> goods) {
    if (goods == null) {
      return new ArrayList<>();
    }

    List<GoodsDto> goodsDtoList = new ArrayList<>();
    for (Goods goodsOne : goods) {
      goodsDtoList.add(GoodsDto.fromEntity(goodsOne));
    }

    return goodsDtoList;
  }

  public static GoodsDto fromEntity(Goods goods) {
    return GoodsDto.builder()
        .id(goods.getId())
        .goodsName(goods.getGoodsName())
        .description(goods.getDescription())
        .price(goods.getPrice())
        .availableQuantity(goods.getAvailableQuantity())
        .goodsStatus(goods.getGoodsStatus())
        .sellerId(goods.getSeller().getId())
        .build();
  }
}
