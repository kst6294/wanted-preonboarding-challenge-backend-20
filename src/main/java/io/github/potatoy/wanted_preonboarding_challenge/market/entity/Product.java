package io.github.potatoy.wanted_preonboarding_challenge.market.entity;

import io.github.potatoy.wanted_preonboarding_challenge.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
  // 제품명, 가격, 예약 상태

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private Long price;

  @CreatedDate
  @Column(name = "create_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "state", nullable = false)
  private State state;

  @ManyToOne
  @JoinColumn(name = "seller_user", nullable = false)
  private User sellerUser;

  @ManyToOne
  @JoinColumn(name = "buyer_user")
  private User buyerUser;

  @Builder
  public Product(User sellerUser, String name, Long price) {
    this.sellerUser = sellerUser;
    this.name = name;
    this.price = price;
    this.state = State.SALE;
  }

  /**
   * 구매자에 대한 정보를 업데이트
   *
   * @param buyerUser 구매할 유저
   * @return Product
   */
  public Product updateBuyerUser(User buyerUser) {
    this.buyerUser = buyerUser;
    this.state = State.RESERVATION;

    return this;
  }

  /**
   * User 객체가 판매자 객체와 동일한지 확인
   *
   * @param user User 객체
   * @return boolean
   */
  public boolean isSellerEquals(User user) {
    return this.sellerUser.equals(user);
  }

  /**
   * User 객체가 구매자 객체와 동일한지 확인
   *
   * @param user User 객체
   * @return boolean
   */
  public boolean isBuyerEquals(User user) {
    return user.equals(this.buyerUser);
  }
}
