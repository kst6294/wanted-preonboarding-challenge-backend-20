package com.market.wanted.order;

import com.market.wanted.item.Item;
import com.market.wanted.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUYER_ID")
    private Member buyer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_ID")
    private Member seller;
    private Long itemId;
    private String itemName;
    private Long itemPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.SALES_APPROVAL_WAITING;

    @Builder
    private Order(Member buyer, Member seller, Item item) {
        this.buyer = buyer;
        this.seller = seller;
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.itemPrice = item.getPrice();
    }

    public void salesApproval(){
        status = OrderStatus.PURCHASE_CONFIRMATION_WAITING;
    }

    public void purchaseConfirmation(){
        status = OrderStatus.COMPLETE;
    }
}
