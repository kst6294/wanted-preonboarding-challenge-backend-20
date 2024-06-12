package wanted.challenge.goods.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.entity.GoodsStatus;
import wanted.challenge.goods.mapper.GoodsMapper;
import wanted.challenge.goods.repository.GoodsRepository;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.mypage.repository.MemberRepository;
import wanted.challenge.order.entity.Orders;
import wanted.challenge.order.service.OrderService;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class GoodsServiceTest {

    protected GoodsMapper mapper = new GoodsMapper();

    @Mock
    protected MemberRepository memberRepository;

    @Mock
    protected GoodsRepository goodsRepository;

    @Mock
    protected OrderService orderService;

    protected GoodsService goodsService;


    @BeforeEach
    void setUp() {
        goodsService = new GoodsService(memberRepository, goodsRepository, orderService, mapper); // 수동으로 주입
    }

    protected Member createTestSeller(Long sellerId) {
        Member seller = new Member();
        seller.setMemberId(sellerId);
        return seller;
    }
    protected Member createTestBuyer(Long buyerId) {
        Member buyer = new Member();
        buyer.setMemberId(buyerId);
        return buyer;
    }
    protected Goods createTestGoods(Long goodsId, Member seller) {
        Goods goods = new Goods();
        goods.setGoodsId(goodsId);
        goods.setSeller(seller);
        goods.setGoodsName("goodsName");
        goods.setGoodsPrice(1000);
        goods.setReservedStatus(GoodsStatus.SALE);
        goods.setQuantity(10);
        return goods;
    }

    protected List<Orders> createTestTradeList(Member buyer, Goods goods) {
        Orders order = new Orders();
        order.setBuyer(buyer);
        order.setGoods(goods);
        return List.of(order);
    }

}