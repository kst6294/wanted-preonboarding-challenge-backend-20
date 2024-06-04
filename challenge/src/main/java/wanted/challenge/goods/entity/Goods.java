package wanted.challenge.goods.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.mypage.entity.Orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long goodsId;

    private String goodsName;

    private int goodsPrice;

    private GoodsStatus reservedStatus = GoodsStatus.SALE;

    private int quantity;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    //memberId
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @OneToMany(mappedBy = "goods")
    private List<Orders> orderList = new ArrayList<>();

    public Goods(String goodsName, int goodsPrice, GoodsStatus reservedStatus, int quantity) {
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.reservedStatus = reservedStatus;
        this.quantity = quantity;
    }

    // 제품 추가되면 member에도 추가
    // 추가되면, User에도 추가해야함.
    public void setSeller(Member member) {
        this.seller = member;
        member.getGoodsList().add(this);
    }
    // 제거하면 User에도 제거해야함.
    public void removeSeller(Member member) {
        member.getGoodsList().remove(this);
        this.seller = null;
    }
}
