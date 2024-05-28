package com.backend.market.Service.purchaseList;


import com.backend.market.DAO.Entity.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface purchaseListRepository extends JpaRepository<PurchaseList,Long> {
    List<PurchaseList> findAllByBuyerId(Long id);
    List<PurchaseList> findAllBySellerId(Long id);

}
