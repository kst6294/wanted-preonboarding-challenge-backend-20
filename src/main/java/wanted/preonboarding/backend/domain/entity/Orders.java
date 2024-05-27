package wanted.preonboarding.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.preonboarding.backend.domain.BaseTimeEntity;
import wanted.preonboarding.backend.dto.request.OrderSaveRequest;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;
    private int price;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    public static Orders from(final OrderSaveRequest orderSaveRequest, final Member member, final Item item) {
        return Orders.builder()
                .price(orderSaveRequest.getPrice())
                .status(OrderStatus.RESERVED)
                .member(member)
                .item(item)
                .build();
    }

    //주문 상태를 예약 상태에서 완료 상태로 변경 - 구매 확정 후
    public void changeStatus(final OrderStatus status) {
        this.status = status;
    }

    //주문 상태
    public enum OrderStatus {
        RESERVED,//구매자가 구매 버튼 클릭 (구매 예약)
        APPROVED,//판매자가 판매 승인
        COMPLETE,//구매자가 구매 승인 (구매 완료)
    }
}
