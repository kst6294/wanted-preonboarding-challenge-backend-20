package com.backend.market.Service.purchaseList;

import com.backend.market.DAO.Entity.PurchaseList;
import com.backend.market.Request.ProductReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class PurchaseListService {

    private final purchaseListRepository purchaseListRepository;

    //판매승인이 되면 거래내역 등록
    public void addPurchasList(ProductReq productReq, Long buy_id)
    {
        PurchaseList purchaseList = new PurchaseList();
        //등록한 판매자의 아이디
        Long id = productReq.getMember().getUserId();

        purchaseList.setProduct_id(productReq.getProduct_id());
        purchaseList.setBuyer_id(buy_id);
        purchaseList.setSeller_id(id);
        purchaseList.setCreaeDate(LocalDate.now());

        this.purchaseListRepository.save(purchaseList);
    }
}
