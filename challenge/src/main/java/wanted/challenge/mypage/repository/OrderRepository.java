package wanted.challenge.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.challenge.mypage.entity.Orders;

import javax.swing.text.html.Option;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    //sellOrderList
    List<Orders> findByGoods_Seller_MemberId(Long memberId);

    //buyOrderList
    List<Orders> findByBuyer_MemberId(Long memberId);

    List<Orders> findByBuyer_MemberIdAndGoods_Seller_MemberId(Long buyerId, Long sellerId);
}
