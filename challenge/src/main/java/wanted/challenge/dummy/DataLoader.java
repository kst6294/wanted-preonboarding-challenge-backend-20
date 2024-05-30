package wanted.challenge.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import wanted.challenge.goods.entity.Goods;
import wanted.challenge.goods.repository.GoodsRepository;
import wanted.challenge.mypage.entity.Member;
import wanted.challenge.mypage.entity.Orders;
import wanted.challenge.mypage.repository.MemberRepository;
import wanted.challenge.mypage.repository.OrderRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final GoodsRepository goodsRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        // 더미 Member 데이터
        Member member1 = new Member();
        member1.setName("John Doe");
        member1.setCreatedAt(LocalDateTime.now());
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("Jane Smith");
        member2.setCreatedAt(LocalDateTime.now());
        memberRepository.save(member2);

        // 더미 Goods 데이터
        Goods goods1 = new Goods("Laptop", 1500, "Available", 10);
        goods1.setSeller(member1);
        goodsRepository.save(goods1);

        Goods goods2 = new Goods("Smartphone", 800, "Reserved", 20);
        goods2.setSeller(member2);
        goodsRepository.save(goods2);

        Goods goods3 = new Goods("Tablet", 600, "Available", 15);
        goods3.setSeller(member1);
        goodsRepository.save(goods3);
// 더미 Orders 데이터
        Orders order1 = new Orders("Laptop", 1500, 1, "finish");
        order1.setGoods(goods1);
        order1.setBuyer(member2);
        order1.setOrderDate(LocalDateTime.now());
        order1.setConfirmDate(LocalDateTime.now().plusDays(1));
        order1.setFinishDate(LocalDateTime.now().plusDays(2));
        orderRepository.save(order1);

        Orders order2 = new Orders("Smartphone", 800, 2, "order");
        order2.setGoods(goods2);
        order2.setBuyer(member1);
        order2.setOrderDate(LocalDateTime.now());
        orderRepository.save(order2);

    }
}
