package wanted.challenge.goods.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.order.entity.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long goodsId;

    private String goodsName;

    private int goodsPrice;

    private String reservedStatus;

    private int quantity;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    //memberId
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    private List<Order> orderList = new ArrayList<>();

    public Goods(String goodsName, int goodsPrice, String reservedStatus, int quantity) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.reservedStatus = reservedStatus;
        this.quantity = quantity;
    }

    // 제품 추가되면 member에도 추가
    // 추가되면, User에도 추가해야함.
    public void setMember(Member member) {
        this.member = member;
        member.getGoodsList().add(this);
    }
    // 제거하면 User에도 제거해야함.
    public void removeUser(Member member) {
        member.getGoodsList().remove(this);
        this.member = null;
    }
}
