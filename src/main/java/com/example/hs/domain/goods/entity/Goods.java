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
  private int quantity;

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
    this.quantity = request.getQuantity();
    this.goodsStatus = request.getGoodsStatus();
  }
}
