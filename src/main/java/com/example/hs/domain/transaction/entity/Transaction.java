package com.example.hs.domain.transaction.entity;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.base.BaseEntity;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.transaction.type.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
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
public class Transaction extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "goods_id")
  private Goods goods;

  @ManyToOne
  @JoinColumn(name = "buyer_id")
  private Member buyer;

  private LocalDateTime transactionCompleteDateTime;
  private int priceAtPurchase;

  @Enumerated(EnumType.STRING)
  private TransactionStatus transactionStatus;
  private int quantity;

  public void updateTransaction(TransactionStatus transactionStatus) {
    this.transactionStatus = transactionStatus;
    this.transactionCompleteDateTime = LocalDateTime.now();
  }
}
