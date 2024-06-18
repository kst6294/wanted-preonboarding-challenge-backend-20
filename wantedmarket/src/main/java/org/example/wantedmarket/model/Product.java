package org.example.wantedmarket.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.wantedmarket.status.ProductStatus;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name="seller_id")
    private User seller;

    @Builder
    public Product(String name, Integer price, Integer quantity, ProductStatus status, User seller) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.seller = seller;
    }

    public void modifyStatus(ProductStatus status) {
        this.status = status;
    }

    public void modifyQuantity(Integer quantity) {
        this.quantity -= quantity;
    }

    public void modifyPrice(Integer price) {
        this.price = price;
    }

}
