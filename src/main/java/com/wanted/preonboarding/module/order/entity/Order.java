package com.wanted.preonboarding.module.order.entity;


import com.wanted.preonboarding.module.common.entity.BaseEntity;
import com.wanted.preonboarding.module.exception.order.InvalidOrderException;
import com.wanted.preonboarding.module.order.enums.OrderStatus;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "ORDERS")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private long id;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_ID", nullable = false)
    private Users seller;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUYER_ID", nullable = false)
    private Users buyer;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS", nullable = false)
    private OrderStatus orderStatus;

    public void setBuyer(Users buyer) {
        if (this.seller.equals(buyer)) {
            throw new InvalidOrderException();
        }
        this.buyer = buyer;
    }


}
