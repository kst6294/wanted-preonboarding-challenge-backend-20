package com.backend.market.Service.purchaseList;


import com.backend.market.DAO.Entity.PurchaseList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface purchaseListRepository extends JpaRepository<PurchaseList,Long> {
}
