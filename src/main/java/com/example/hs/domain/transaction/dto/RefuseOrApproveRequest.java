package com.example.hs.domain.transaction.dto;

import com.example.hs.domain.goods.type.GoodsStatusDeserializer;
import com.example.hs.domain.transaction.type.TransactionStatus;
import com.example.hs.domain.transaction.type.TransactionStatusDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefuseOrApproveRequest {
  @NotNull(message = "거래 id는 필수입니다.")
  private long transactionId;


  @NotNull(message = "거래 상태는 필수 입니다.")
  @JsonDeserialize(using = TransactionStatusDeserializer.class)
  private TransactionStatus transactionStatus;
}
