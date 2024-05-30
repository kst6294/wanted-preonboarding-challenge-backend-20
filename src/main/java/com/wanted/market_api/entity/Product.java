package com.wanted.market_api.entity;

import com.wanted.market_api.constant.ProductStatus;
import com.wanted.market_api.dto.request.product.ProductCreateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Entity
@SuperBuilder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int price;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Order> orders;
    private int count;

    public void decrementCount() {
        this.count--;

        if (this.count == 0 && this.productStatus.equals(ProductStatus.IN_SALE)) {
            this.productStatus = ProductStatus.IN_RESERVATION;
        }
    }

    public void incrementCount() {
        this.count++;

        if ((this.productStatus.equals(ProductStatus.COMPLETE)
                || this.productStatus.equals(ProductStatus.IN_RESERVATION))
                && this.count != 0) {
            this.productStatus = ProductStatus.IN_SALE;
        }
    }

    public void completeSale() {
        this.productStatus = ProductStatus.COMPLETE;
    }
}
