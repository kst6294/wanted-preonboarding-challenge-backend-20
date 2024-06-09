package wanted.market.domain.product.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.global.entity.BaseEntity;

import static wanted.market.domain.product.repository.entity.ReservationStatus.*;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JoinColumn(name="member_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    private int price;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private int remainQuantity;

    private int completeQuantity;

    private String content;

    @Builder
    private Product(String name, Member member, int price, ReservationStatus reservationStatus, int remainQuantity, int completeQuantity, String content) {
        this.name = name;
        this.member = member;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.remainQuantity = remainQuantity;
        this.completeQuantity = completeQuantity;
        this.content = content;
    }

    // 거래가 중간에 취소된 경우
    public void bringBackQuantity() {
        if (remainQuantity == 1) this.reservationStatus = SALE;
    }

    // 거래를 시작하는 경우
    public void updateReservationStatus() {
        if (this.remainQuantity == 1) this.reservationStatus = RESERVATION;
    }

    public void completeTransaction() {
        remainQuantity--;
        completeQuantity++;
        if (remainQuantity == 0) reservationStatus = COMPLETED;
    }

    public void updateProduct(int price) {
        this.price = price;
    }
}
