package wanted.market.domain.product.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wanted.market.domain.product.repository.entity.ReservationStatus;

@Getter
@AllArgsConstructor
public class ProductListResponse {
    private String name;

    private int price;

    private ReservationStatus reservationStatus;
}
