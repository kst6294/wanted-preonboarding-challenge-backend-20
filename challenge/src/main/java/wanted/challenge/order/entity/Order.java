package wanted.challenge.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.mypage.entity.Member;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String goodsName;

    private int orderPrice;

    private int quantity;

    private String orderStatus;

    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime confirmDate;

    private LocalDateTime finishDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    public Order(String goodsName, int orderPrice, int quantity, String orderStatus) {
        this.goodsName = goodsName;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
    }

    // 주문 추가되면 member에도 추가
    public void setMember(Member member) {
        this.member = member;
        member.getOrderList().add(this);
    }

    // 주문 추가되면 goods에도 추가
    public void setGoods(Goods goods) {
        this.goods = goods;
        goods.getOrderList().add(this);
    }

    //주문 취소는 고려하지않음
}
