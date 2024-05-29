package com.wanted.preonboarding.module.order.entity;


import com.wanted.preonboarding.module.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "ORDERS_PRODUCT_SNAPSHOT")
@Entity
public class OrderProductSnapShot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private long id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "PRICE", nullable = false)
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;


    public static OrderProductSnapShot fromProduct(Product product, Order order) {
        return OrderProductSnapShot.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .product(product)
                .order(order)
                .build();
    }


}
