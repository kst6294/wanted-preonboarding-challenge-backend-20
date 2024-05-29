package com.example.demo.repository;

import com.example.demo.entity.Buy;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyRespository extends JpaRepository<Buy, Long> {



    @Query("SELECT b FROM Buy b JOIN FETCH b.item WHERE (b.purchaseId = :purchaseId AND b.sellId = :sellId) OR (b.purchaseId = :sellId AND b.sellId = :purchaseId)")
    List<Buy> findHistory(@Param("sellId") Long sellId,
                          @Param("purchaseId") Long purchaseId);


    List<Buy> findByPurchaseIdAndItemState(Long purchaseId, ItemState itemState);

    @Query("SELECT b FROM Buy b JOIN FETCH b.item WHERE b.itemState = :itemState AND (b.purchaseId = :purchaseId OR b.sellId = :purchaseId)")
    List<Buy> findByReservedIdAndItemState(@Param("purchaseId") Long purchaseId,
                                           @Param("itemState") ItemState itemState);


    List<Buy> findByPurchaseIdAndItem(Long purchaseId, Item item);


    List<Buy> findByIdAndSellId(Long id, Long sellId);
    List<Buy> findByIdAndPurchaseId(Long id, Long purchaseId);

    List<Buy> findByItem(Item item);

}

