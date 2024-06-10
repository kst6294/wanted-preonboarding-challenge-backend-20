package com.example.hs.domain.goods.dto;

import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.entity.Transaction;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDtoForSeller extends GoodsDto{
  private List<TransactionDto> transactions;

  public static GoodsDtoForSeller fromEntity(Goods goods, List<Transaction> transactions) {
    List<TransactionDto> transactionDtos = transactions.stream()
        .map(TransactionDto::fromEntity)
        .collect(Collectors.toList());

    return GoodsDtoForSeller.builder()
        .id(goods.getId())
        .goodsName(goods.getGoodsName())
        .description(goods.getDescription())
        .price(goods.getPrice())
        .quantity(goods.getQuantity())
        .goodsStatus(goods.getGoodsStatus())
        .sellerId(goods.getSeller().getId())
        .transactions(transactionDtos)
        .build();
  }
}
