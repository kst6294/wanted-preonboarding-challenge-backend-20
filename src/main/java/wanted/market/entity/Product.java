package wanted.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {

    public Product() {
        this.productStatus = ProductStatus.AVAILABLE;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제품명
    private String name;

    // 가격
    private int price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    // 예약상태
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    public void reserve() {
        this.productStatus = ProductStatus.RESERVED;
    }

    public void sell() {
        this.productStatus = ProductStatus.SOLD_OUT;
    }
}
