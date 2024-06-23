package kr.co.wanted.market.trade.entity;

import jakarta.persistence.*;
import kr.co.wanted.market.common.global.entity.BaseEntity;
import kr.co.wanted.market.common.global.error.BizException;
import kr.co.wanted.market.common.global.utils.ContextUtil;
import kr.co.wanted.market.member.entity.Member;
import kr.co.wanted.market.product.entity.Product;
import kr.co.wanted.market.product.repository.ProductRepository;
import kr.co.wanted.market.trade.enums.TradeState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

import static kr.co.wanted.market.common.global.enums.ErrorCode.*;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "TRADE_IDX_1", columnList = "product_id"),
                @Index(name = "TRADE_IDX_2", columnList = "buyer_id, product_id"),
                @Index(name = "TRADE_IDX_3", columnList = "seller_id, product_id")
        }
)
public class Trade extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "buyer_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "seller_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Product product;

    @Enumerated(EnumType.STRING)
    private TradeState state;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;


    public Trade(Member buyer,
                 Member seller,
                 Product product,
                 Long price,
                 Long quantity) {

        this.buyer = buyer;
        this.seller = seller;
        this.product = product;
        this.price = price;
        this.quantity = quantity;

        this.state = TradeState.REQUEST;
    }


    /**
     * 판매를 승인한다.
     * 판매자만 가능하다.
     */
    public void approveSelling() {

        Long memberId = ContextUtil.getMemberId()
                .orElseThrow(() -> new BizException(NO_PERMISSION));

        // 판매자만 판매승인 가능
        if (!memberId.equals(this.seller.getId())) {
            throw new BizException(NO_PERMISSION);
        }

        // 구매요청 상태에서만 변경 가능
        if (this.state != TradeState.REQUEST) {
            throw new BizException(TRADE_STATE_CAN_NOT_CHANGED);
        }

        this.state = this.state.next()
                .orElseThrow(() -> new BizException(TRADE_STATE_CAN_NOT_CHANGED));
    }


    /**
     * 구매 확정한다. 구매자만 가능하다.
     * 예약 상태에서만 가능, 호출이후 상품의 상태를 일관성 있게 변경하기 위해 반드시
     * {@link ProductRepository#updateStateAfterTradeConfirmation(Long)} 를 사용해 재고상태에 따라 상태를 변경해주어야 한다.
     */
    public void confirm() {

        Long memberId = ContextUtil.getMemberId()
                .orElseThrow(() -> new BizException(NO_PERMISSION));

        // 구매자만 구매확정 가능
        if (!memberId.equals(this.buyer.getId())) {
            throw new BizException(NO_PERMISSION);
        }

        // 예약상태에서만 구매확정 가능
        if (this.state != TradeState.BOOKING) {
            throw new BizException(TRADE_STATE_CAN_NOT_CHANGED);
        }

        this.state = this.state.next()
                .orElseThrow(() -> new BizException(TRADE_STATE_CAN_NOT_CHANGED));
    }


    /**
     * 거래 상태를 되돌린다.
     * 구매자는 거래요청 상태, 판매자는 예약중 상태에서만 가능하다.
     * 구매자가 거래요청 상태에서 취소시 상품의 재고상태를 다시 증가시키기 위해 반드시
     * {@link ProductRepository#plusQuantityById(Long, Long)} 를 사용해야 한다.
     */
    public void revertState() {

        Long memberId = ContextUtil.getMemberId()
                .orElseThrow(() -> new BizException(NO_PERMISSION));

        // 구매자라면 거래요청 상태에서만,
        if ((Objects.equals(memberId, this.buyer.getId()) && this.state == TradeState.REQUEST)
        // 판매자라면 예약중 상태에서만 가능하다.
                || (Objects.equals(memberId, this.seller.getId()) && this.state == TradeState.BOOKING)) {

            this.state = this.state.previous()
                    .orElseThrow(() -> new BizException(TRADE_STATE_CAN_NOT_CHANGED));

            return;
        }

        throw new BizException(TRADE_STATE_CAN_NOT_CHANGED);
    }


    @PrePersist
    private void checkPrePersist() {

        ContextUtil.getMemberId()
                .orElseThrow(() -> new BizException(NO_PERMISSION));

        // 구매자와 판매자가 같을 수 없다.
        if (Objects.equals(buyer, seller)) {
            throw new BizException(TRADE_NOT_PROCESSED);
        }
    }


    @PreRemove
    private void checkPreRemove() {

        Long memberId = ContextUtil.getMemberId()
                .orElseThrow(() -> new BizException(NO_PERMISSION));

        if (!memberId.equals(buyer.getId())) {
            throw new BizException(NO_PERMISSION);
        }
    }
}
