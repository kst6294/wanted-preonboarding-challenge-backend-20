package wanted.challenge.mypage.entity;

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
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String goodsName;

    private int orderPrice;

    private int quantity;

    private OrderStatus orderStatus = OrderStatus.ORDER;

    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime confirmDate;

    private LocalDateTime finishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;


    public Orders(String goodsName, int orderPrice, int quantity, OrderStatus orderStatus) {
        this.goodsName = goodsName;
        this.orderPrice = orderPrice;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
    }

    // 주문 추가되면 member에도 추가
    public void setBuyer(Member member) {
        this.buyer = member;
        member.getOrderList().add(this);
    }

    // 주문 추가되면 goods에도 추가
    public void setGoods(Goods goods) {
        this.goods = goods;
        goods.getOrderList().add(this);
    }

    public void setSeller(Member member) {
        this.goods.setSeller(member);
    }

    public Member getSeller() {
        return this.goods.getSeller();
    }

    public Long getSellerId() {
        return this.goods.getSeller().getMemberId();
    }

    //주문 취소는 고려하지않음
}
