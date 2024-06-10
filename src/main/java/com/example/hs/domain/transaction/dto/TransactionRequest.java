package com.example.hs.domain.transaction.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
  @NotNull(message = "주문할 상품 id는 필수입니다.")
  private long goodsId;

  @NotNull(message = "주문할 상품 가격은 필수입니다.")
  private int price;

  @NotNull(message = "주문할 상품 갯수는 필수이고 1개여야합니다.")
  @Min(value = 1, message = "주문할 상품 갯수는 1개여야합니다.")
  @Max(value = 1, message = "주문할 상품 갯수는 1개여야합니다.")
  private int quantity;
}
