package wanted.Market.domain.transaction.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.Market.domain.member.entity.Member;
import wanted.Market.domain.product.entity.Product;
import wanted.Market.domain.product.entity.ProductStatus;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buyerName;
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
