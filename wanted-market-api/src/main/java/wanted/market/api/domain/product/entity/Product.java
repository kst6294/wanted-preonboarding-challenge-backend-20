package wanted.market.api.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.api.domain.product.enums.ProductStatus;
import wanted.market.api.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name= "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column
    private Long price;
    @Column
    private Long count;
    @Column
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @Column
    private LocalDateTime registerTime;
    @ManyToOne
    @JoinColumn(name ="seller_id")
    private User user;


    @Builder
    public Product(String name, Long price, Long count, User user){
        this.name = name;
        this.price = price;
        this.count = count;
        this.user = user;
        this.productStatus = ProductStatus.SALE;
        this.registerTime = LocalDateTime.now();
    }

    public void modifiedPrice(Long price){
        this.price = price;
    }
}
