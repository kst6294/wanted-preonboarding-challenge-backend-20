package org.example.wantedmarket.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.wantedmarket.status.OrderStatus;
import org.example.wantedmarket.status.ProductStatus;

@Entity
@Getter
@NoArgsConstructor
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(Product product, User seller, User buyer, OrderStatus status) {
        this.product = product;
        this.seller = seller;
        this.buyer = buyer;
        this.status = status;
    }

    public void modifyStatus(OrderStatus status) {
        this.status = status;
    }

}
