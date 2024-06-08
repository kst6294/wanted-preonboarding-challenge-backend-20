package wanted.market.domain.product.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static wanted.market.domain.product.repository.entity.ReservationStatus.RESERVATION;
import static wanted.market.domain.product.repository.entity.ReservationStatus.SALE;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private int quantity;

    private String content;

    private LocalDateTime createdAt;

    @Builder
    private Product(String name, int price, ReservationStatus reservationStatus, int quantity, String content, LocalDateTime createdAt) {
        this.name = name;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.quantity = quantity;
        this.content = content;
        this.createdAt = createdAt;
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
