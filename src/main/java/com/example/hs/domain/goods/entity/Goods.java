package com.example.hs.domain.goods.entity;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.base.BaseEntity;
import com.example.hs.domain.goods.dto.GoodsEditRequest;
import com.example.hs.domain.goods.type.GoodsStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Builder
@Getter
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Goods extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String goodsName;
  private String description;
  private int price;
  @Setter
  private int totalTransactionQuantity;
  private int availableQuantity;
  private int reservedQuantity; // availableQuantity안에서 예약되어있음

  @Setter
  @Enumerated(EnumType.STRING)
  private GoodsStatus goodsStatus;

  @ManyToOne
  @JoinColumn(name = "seller_id")
  private Member seller;

  public void updateGoods(GoodsEditRequest request) {
    this.goodsName = request.getGoodsName();
    this.description = request.getDescription();
    this.price = request.getPrice();
    this.availableQuantity = request.getAvailableQuantity();
    this.goodsStatus = request.getGoodsStatus();
  }


  public void purchaseByBuyer(int changeAmount) {
    this.reservedQuantity += changeAmount;
    if (this.reservedQuantity == this.availableQuantity) {
      this.goodsStatus = GoodsStatus.RESERVED;
    }
  }

  public void refusalOfSaleBySeller(int changeAmount) {
    this.reservedQuantity -= changeAmount;
    if (this.goodsStatus == GoodsStatus.RESERVED && this.availableQuantity > 0) {
      this.goodsStatus = GoodsStatus.SALE;
    }
  }

  public void approvalOfSaleBySeller(int changeAmount) {
    this.reservedQuantity -= changeAmount;
  }

  public void confirmPurchaseByBuyer(int changeAmount) {
    this.availableQuantity -= changeAmount;
    this.totalTransactionQuantity += changeAmount;

    if (this.reservedQuantity == 0 && this.availableQuantity == 0) {
      this.goodsStatus = GoodsStatus.SOLD_OUT;
    }
  }

  public void cancelPurchaseByBuyer(int changeAmount) {
    // TODO 거래내역이 있을 경우 거래 취소

    if (this.availableQuantity -this.reservedQuantity > 0) {
      this.goodsStatus = GoodsStatus.SALE;
    }
  }
}
