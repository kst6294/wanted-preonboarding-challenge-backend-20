package wanted.preonboarding.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import wanted.preonboarding.backend.domain.BaseTimeEntity;
import wanted.preonboarding.backend.dto.request.ItemSaveRequest;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private ItemStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Item from(ItemSaveRequest itemSaveRequest, Member member) {
        return Item.builder()
                .name(itemSaveRequest.getName())
                .price(itemSaveRequest.getPrice())
                .status(ItemStatus.FOR_SALE)
                .member(member)
                .build();
    }

    //현재 판매중 상태인지 검증
    public boolean validateForSale() {
        return ItemStatus.FOR_SALE.equals(this.status);
    }

    public enum ItemStatus {
        FOR_SALE("판매중"),
        RESERVED("예약중"),
        COMPLETE("완료"),
        ;

        private final String status;

        ItemStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
