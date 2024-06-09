package wanted.market.domain.transcation.repository.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.market.domain.member.repository.entity.Member;
import wanted.market.domain.product.repository.entity.Product;
import wanted.market.global.entity.BaseEntity;

import static wanted.market.domain.transcation.repository.entity.TransactionStatus.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name="member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name="product_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private int price;

    @Builder
    private Transaction(Member member, Product product, TransactionStatus transactionStatus, int price) {
        this.member = member;
        this.product = product;
        this.transactionStatus = transactionStatus;
        this.price = price;
    }

    public void cancelTransaction() {
        this.transactionStatus = TRADE_CANCEL;
    }

    public void completeTransaction() {
        this.transactionStatus = TRADE_COMPLETE;
    }
}
