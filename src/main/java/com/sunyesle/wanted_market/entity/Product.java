package com.sunyesle.wanted_market.entity;

import com.sunyesle.wanted_market.enums.ProductStatus;
import com.sunyesle.wanted_market.exception.ErrorCodeException;
import com.sunyesle.wanted_market.exception.ProductErrorCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String name;

    private Integer price;

    private Integer quantity;

    private Integer availableQuantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public void buy(Integer quantity) {
        int newAvailableQuantity = this.availableQuantity - quantity;
        if (newAvailableQuantity < 0) {
            throw new ErrorCodeException(ProductErrorCode.OUT_OF_STOCK);
        }
        if (newAvailableQuantity == 0) {
            this.status = ProductStatus.RESERVED;
        }
        this.availableQuantity = newAvailableQuantity;
    }

    public void accept(boolean hasNoOtherOpenOffers) {
        if (availableQuantity == 0 && hasNoOtherOpenOffers) {
            this.status = ProductStatus.COMPLETED;
        }
    }

    public void decline(Integer quantity) {
        this.availableQuantity = this.availableQuantity + quantity;
        this.status = ProductStatus.AVAILABLE;
    }
}
