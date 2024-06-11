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

  private int totalTransactionQuantity;
  private int reservedQuantity; // availableQuantity안에서 예약되어있음

  public static GoodsDtoForSeller fromEntity(Goods goods, List<Transaction> transactions) {
    List<TransactionDto> transactionDtos = transactions.stream()
        .map(TransactionDto::fromEntity)
        .collect(Collectors.toList());

    return GoodsDtoForSeller.builder()
        .id(goods.getId())
        .goodsName(goods.getGoodsName())
        .description(goods.getDescription())
        .price(goods.getPrice())
        .availableQuantity(goods.getAvailableQuantity())
        .goodsStatus(goods.getGoodsStatus())
        .sellerId(goods.getSeller().getId())
        .totalTransactionQuantity(goods.getTotalTransactionQuantity())
        .reservedQuantity(goods.getReservedQuantity())
        .transactions(transactionDtos)
        .build();
  }
}
