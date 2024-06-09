package wanted.market.domain.product.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import wanted.market.domain.product.repository.entity.ReservationStatus;
import wanted.market.domain.product.service.dto.request.ProductRegisterServiceRequest;
import jakarta.validation.constraints.Positive;

import static wanted.market.domain.product.repository.entity.ReservationStatus.*;

@Getter
public class ProductRegisterRequest {
    private String name;

    @Positive(message = "상품 가격은 양수여야 합니다.")
    private int price;

    private ReservationStatus reservationStatus;

    @Positive(message = "상품 수량은 0보다 커야 합니다.")
    private int quantity;

    private String content;

    @Builder
    private ProductRegisterRequest(String name, int price, int quantity, String content) {
        this.name = name;
        this.price = price;
        this.reservationStatus = SALE;
        this.quantity = quantity;
        this.content = content;
    }

    public ProductRegisterServiceRequest toService(String email) {
        return ProductRegisterServiceRequest.builder()
                .name(name)
                .email(email)
                .price(price)
                .reservationStatus(reservationStatus)
                .quantity(quantity)
                .content(content)
                .build();
    }
}
