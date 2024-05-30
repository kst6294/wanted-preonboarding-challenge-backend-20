package org.example.preonboarding.order.model.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.preonboarding.common.entity.BaseEntity;
import org.example.preonboarding.member.model.entity.Member;
import org.example.preonboarding.order.model.enums.OrderStatus;
import org.example.preonboarding.product.model.entity.Product;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Builder
    public Order(Member buyer, Member seller, Product product, OrderStatus orderStatus, int totalPrice, LocalDateTime orderedAt) {
        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderedAt = orderedAt;
    }

    public Order(Product product, Member buyer,LocalDateTime orderedAt) {
        this.buyer = buyer;
        this.seller = product.getSeller();
        this.orderStatus = OrderStatus.INIT;
        this.orderedAt = orderedAt;
        this.totalPrice = product.getPrice();
        this.product = product;
    }

    public static Order create(Product product, Member buyer, LocalDateTime orderedAt) {
        return new Order(product, buyer, orderedAt);
    }

    public void approveOrder() {
        this.orderStatus = OrderStatus.APPROVED;
    }
}
