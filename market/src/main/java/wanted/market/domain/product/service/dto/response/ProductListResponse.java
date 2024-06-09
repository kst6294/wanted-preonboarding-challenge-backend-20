package wanted.market.domain.product.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.domain.product.repository.entity.ReservationStatus;

@Getter
public class ProductListResponse {
    private String name;
    private int price;
    private ReservationStatus reservationStatus;

    @Builder
    private ProductListResponse(String name, int price, ReservationStatus reservationStatus) {
        this.name = name;
        this.price = price;
        this.reservationStatus = reservationStatus;
    }

    public static ProductListResponse of(Product product) {
        return ProductListResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .reservationStatus(product.getReservationStatus())
                .build();
    }
}
