package kr.co.wanted.market.product.entity;

import jakarta.persistence.*;
import kr.co.wanted.market.common.global.entity.BaseEntity;
import kr.co.wanted.market.common.global.enums.ErrorCode;
import kr.co.wanted.market.common.global.error.BizException;
import kr.co.wanted.market.common.global.utils.ContextUtil;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.product.enums.ProductState;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import static kr.co.wanted.market.common.global.constants.Constant.*;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "PRODUCT_IDX_1", columnList = "seller_id")
        }
)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "seller_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member seller;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductState state;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;


    public Product(Member seller,
                   String name,
                   Long price,
                   Long quantity) {

        if (!StringUtils.hasText(name)) {
            throw new BizException(ErrorCode.NOT_VALIDATED, "상품명이 없습니다.");
        }
        if (price < PRODUCT_PRICE_MIN || price > PRODUCT_PRICE_MAX) {
            throw new BizException(ErrorCode.NOT_VALIDATED, "가격 허용범위를 벗어납니다.");
        }
        if (quantity < PRODUCT_QUANTITY_MIN || quantity > PRODUCT_QUANTITY_MAX) {
            throw new BizException(ErrorCode.NOT_VALIDATED, "수량 허용범위를 벗어납니다.");
        }

        this.seller = seller;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        // 등록시점의 수량은 최소 1개이므로 판매중 상태
        this.state = ProductState.SALE;
    }


    @PrePersist
    @PreUpdate
    @PreRemove
    private void checkOwner() {

        ContextUtil.getMemberId()
                .filter(seller.getId()::equals)
                .orElseThrow(() -> new BizException(ErrorCode.NO_PERMISSION));
    }

}
