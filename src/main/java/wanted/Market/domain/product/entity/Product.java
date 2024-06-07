package wanted.Market.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.Market.domain.member.entity.Member;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private String sellerIdentifier;
}
