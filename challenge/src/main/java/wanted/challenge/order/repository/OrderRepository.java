package wanted.challenge.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wanted.challenge.order.entity.Orders;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

    //sellOrderList
    List<Orders> findByGoods_Seller_MemberId(Long memberId);

    //buyOrderList
    List<Orders> findByBuyer_MemberId(Long memberId);

    List<Orders> findByBuyer_MemberIdAndGoods_Seller_MemberId(Long buyerId, Long sellerId);
}
