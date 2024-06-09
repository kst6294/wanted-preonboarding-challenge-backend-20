package com.sunyesle.wanted_market.entity;

import com.sunyesle.wanted_market.enums.ProductStatus;
import com.sunyesle.wanted_market.exception.ErrorCodeException;
import com.sunyesle.wanted_market.exception.ProductErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String name;

    private Integer price;

    private Integer quantity;

    private Integer availableQuantity;

    public Product(Long memberId, String name, Integer price, Integer quantity) {
        this.memberId = memberId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.availableQuantity = quantity;
        this.status = ProductStatus.AVAILABLE;
    }

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

    public void decline(Integer quantity) {
        this.availableQuantity = this.availableQuantity + quantity;
        this.status = ProductStatus.AVAILABLE;
    }

    public void complete() {
        this.status = ProductStatus.COMPLETED;
    }
}
