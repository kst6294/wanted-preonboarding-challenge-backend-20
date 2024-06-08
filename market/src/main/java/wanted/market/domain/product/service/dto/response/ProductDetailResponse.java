package wanted.market.domain.product.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.repository.entity.ReservationStatus;

import java.time.LocalDateTime;

@Getter
public class ProductDetailResponse {
    private String name;

    private String email;

    private int price;

    private ReservationStatus reservationStatus;

    private int quantity;

    private String content;

    private LocalDateTime createdDateTime;

    @Builder
    private ProductDetailResponse(String name, String email, int price, ReservationStatus reservationStatus, int quantity, String content, LocalDateTime createdDateTime) {
        this.name = name;
        this.email = email;
        this.price = price;
        this.reservationStatus = reservationStatus;
        this.quantity = quantity;
        this.content = content;
        this.createdDateTime = createdDateTime;
    }

    public static ProductDetailResponse of(Product product) {
        return ProductDetailResponse.builder()
                .name(product.getName())
                .email(product.getMember().getEmail())
                .price(product.getPrice())
                .reservationStatus(product.getReservationStatus())
                .createdDateTime(product.getCreatedDateTime())
                .content(product.getContent())
                .build();
    }
}
