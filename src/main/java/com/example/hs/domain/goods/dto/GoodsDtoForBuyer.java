package com.example.hs.domain.goods.dto;

import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.type.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDtoForBuyer extends GoodsDto{
  private TransactionDto transaction;

  public static GoodsDtoForBuyer fromEntity(Goods goods, Transaction transaction) {

    return GoodsDtoForBuyer.builder()
        .id(goods.getId())
        .goodsName(goods.getGoodsName())
        .description(goods.getDescription())
        .price(goods.getPrice())
        .availableQuantity(goods.getAvailableQuantity())
        .goodsStatus(goods.getGoodsStatus())
        .sellerId(goods.getSeller().getId())
        .transaction(TransactionDto.fromEntity(transaction))
        .build();
  }
}
