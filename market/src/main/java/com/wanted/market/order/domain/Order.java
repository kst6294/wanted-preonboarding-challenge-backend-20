package com.wanted.market.order.domain;

import com.wanted.market.member.domain.Member;
import com.wanted.market.order.dto.OrderRequestDto;
import com.wanted.market.order.model.OrderStatus;
import com.wanted.market.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Table(name="orders")
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int price;

    private int orderQuantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @Builder
    public Order(Member seller, Member buyer, OrderStatus orderStatus, int orderQuantity, Product product) {
        this.seller = seller;
        this.buyer = buyer;
        this.orderStatus = orderStatus;
        this.price = product.getPrice();
        this.orderQuantity = orderQuantity;
        this.product = product;
    }

    public void modifyStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
