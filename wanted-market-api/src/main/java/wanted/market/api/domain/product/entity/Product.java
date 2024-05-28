package wanted.market.api.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.api.domain.product.enums.Status;
import wanted.market.api.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column
    private Long price;
    @Column
    private Long count;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column
    private LocalDateTime registerTime;
    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;


    @Builder
    public Product(String name, Long price, Long count, User user){
        this.name = name;
        this.price = price;
        this.count = count;
        this.user = user;
        this.status = Status.SALE;
        this.registerTime = LocalDateTime.now();
    }
}
