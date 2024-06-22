package org.example.wantedmarket.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.wantedmarket.status.OrderStatus;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private Integer orderedPrice;

    private Long productId;

    private Long sellerId;

    private Long buyerId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Builder
    public Order(Integer quantity, Integer orderedPrice, Long productId, Long sellerId, Long buyerId, OrderStatus orderStatus) {
        this.quantity = quantity;
        this.orderedPrice = orderedPrice;
        this.productId = productId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.orderStatus = orderStatus;
    }

    public void modifyOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void determineOrderedPrice(Integer orderedPrice) {
        this.orderedPrice = orderedPrice;
    }

    public boolean isOrderSeller(Long userId) {
        return this.sellerId.equals(userId);
    }

    public boolean isOrderBuyer(Long userId) {
        return this.buyerId.equals(userId);
    }

}
