package com.example.hs.domain.transaction.dto;

import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.type.TransactionStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
  private long id;
  private long goodsId;
  private long buyerId;
  private LocalDateTime transactionCompleteDateTime;
  private int priceAtPurchase;
  private TransactionStatus transactionStatus;
  private int quantity;

  public static List<TransactionDto> fromEntity(List<Transaction> transactionList) {
    return transactionList.stream()
        .map(TransactionDto::fromEntity)
        .collect(Collectors.toList());
  }

  public static TransactionDto fromEntity(Transaction transaction) {
    return TransactionDto.builder()
        .id(transaction.getId())
        .goodsId(transaction.getGoods().getId())
        .buyerId(transaction.getBuyer().getId())
        .transactionCompleteDateTime(transaction.getTransactionCompleteDateTime())
        .priceAtPurchase(transaction.getPriceAtPurchase())
        .transactionStatus(transaction.getTransactionStatus())
        .quantity(transaction.getQuantity())
        .build();
  }
}
