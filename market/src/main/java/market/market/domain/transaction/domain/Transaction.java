package market.market.domain.transaction.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import market.market.domain.product.domain.Product;
import market.market.global.entity.BaseEntity;

@Entity
@Getter
@Table(name = "tbl_transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction extends BaseEntity {
    private Long buyer_id;
    private String buyer_accountid;
    private Long seller_id;
    private String seller_accountid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int price;

    @Builder
    public Transaction(Long buyer_id, String buyer_accountid, Long seller_id, String seller_accountid, Product product, int price) {
        this.buyer_id = buyer_id;
        this.buyer_accountid = buyer_accountid;
        this.seller_id = seller_id;
        this.seller_accountid = seller_accountid;
        this.product = product;
        this.price = price;
    }
}
