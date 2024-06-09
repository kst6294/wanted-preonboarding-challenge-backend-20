package org.example.wantedmarket.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.wantedmarket.dto.product.ProductCreateDto;
import org.example.wantedmarket.status.ProductStatus;

@Entity
@Getter
@NoArgsConstructor
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User seller;

    @Builder
    public Product(String name, Integer price, ProductStatus status, User seller) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.seller = seller;
    }

    public void modifyStatus(ProductStatus status) {
        this.status = status;
    }

}
