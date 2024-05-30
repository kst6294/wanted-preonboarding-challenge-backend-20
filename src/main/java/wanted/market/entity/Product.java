package wanted.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    // 수량
    private int quantity;

    // 판매자
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
        quantity--;
        if(quantity <= 0) {
            quantity = 0;
            this.productStatus = ProductStatus.SOLD_OUT;
        }
    }
}
