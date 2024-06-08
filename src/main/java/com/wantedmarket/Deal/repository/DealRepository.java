package com.wantedmarket.Deal.repository;

import com.wantedmarket.Deal.domain.Deal;
import com.wantedmarket.Item.type.ItemStatus;
import com.wantedmarket.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, Long> {

    Optional<Deal> findByItemId(Long id);

    List<Deal> findByBuyerAndSellerOrSellerAndBuyer(Member buyer, Member seller, Member buyer1, Member seller1);


    List<Deal> findAllByItemItemStatusAndBuyerUsernameOrSellerUsername(ItemStatus itemStatus,
                                                                       String buyer,
                                                                       String seller);

    List<Deal> findAllByBuyerUsername(String username);
}
