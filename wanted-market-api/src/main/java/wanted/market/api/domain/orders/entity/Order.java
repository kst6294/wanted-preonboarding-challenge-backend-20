package wanted.market.api.domain.orders.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.api.domain.orders.enums.OrderStatus;
import wanted.market.api.domain.product.entity.Product;
import wanted.market.api.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name= "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name ="buyer_id")
    private User user;

    @Column(name = "buy_price")
    private Long price;

    @Column
    private LocalDateTime orderTime;

    @Column
    private Long count;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Builder
    private Order(Product product, User user, Long price, Long count) {
        this.product = product;
        this.user = user;
        this.price = price;
        this.count = count;
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.RESERVED;
    }
    public static Order from(Product product, User user, Long price, Long count){
        return Order.builder()
                .product(product)
                .user(user)
                .price(price)
                .count(count)
                .build();
    }
    public void approve() {
        this.status = OrderStatus.APPROVED;
    }
}
