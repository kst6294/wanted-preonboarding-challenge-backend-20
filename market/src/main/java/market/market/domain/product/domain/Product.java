package market.market.domain.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import market.market.domain.product.enums.Status;
import market.market.domain.transaction.domain.Transaction;
import market.market.domain.user.domain.User;
import market.market.global.entity.BaseEntity;

import java.util.List;

@Entity
@Getter
@Table(name = "tbl_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    private String name; // 제품명

    private int price; // 가격

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // 제품 상태

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 사용자

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Transaction> transactions;

    // 2단계
    private int quantity; // 수량

    @Builder
    public Product(String name, int price, Status status, User user, int quantity) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.user = user;
        this.quantity = quantity;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void updateQuantity() {
        this.quantity -=1;
    }
}
