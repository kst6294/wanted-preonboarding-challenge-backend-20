package wanted.market.domain.product.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.service.dto.response.ProductDetailResponse;
import wanted.market.global.entity.BaseEntity;

import static wanted.market.domain.product.repository.entity.ReservationStatus.RESERVATION;
import static wanted.market.domain.product.repository.entity.ReservationStatus.SALE;

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

    private int quantity;

    private String content;

    @Builder
    private Product(String name, Member member, int price, ReservationStatus reservationStatus, int quantity, String content) {
        this.name = name;
        this.member = member;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.quantity = quantity;
        this.content = content;
    }

    public void bringBackQuantity() {
        this.quantity = quantity+1;
        if (this.reservationStatus == RESERVATION) this.reservationStatus = SALE;
    }

    public void updateReservationStatusAndQuantity() {
        if (this.quantity == 1) this.reservationStatus = RESERVATION;
        this.quantity = quantity-1;
    }
}
