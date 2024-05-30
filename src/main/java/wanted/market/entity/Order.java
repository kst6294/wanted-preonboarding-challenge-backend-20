package wanted.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    public Order() {
        this.orderDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.REQUEST;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 구매당시 가격
    private int buyPrice;

    public void approve() {
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void confirm() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    private LocalDateTime orderDate;
}
