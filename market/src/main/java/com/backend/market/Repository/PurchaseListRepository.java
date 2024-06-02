package com.backend.market.Repository;


import com.backend.market.DAO.Entity.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseListRepository extends JpaRepository<PurchaseList,Long> {
    List<PurchaseList> findAllByBuyerId(Long id);
    List<PurchaseList> findAllBySellerId(Long id);

    Optional<PurchaseList> findByProductId(Long id);

}
