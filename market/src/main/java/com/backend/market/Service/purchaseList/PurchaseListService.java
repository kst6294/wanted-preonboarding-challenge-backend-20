package com.backend.market.Service.purchaseList;

import com.backend.market.DAO.Entity.Product;
import com.backend.market.DAO.Entity.PurchaseList;
import com.backend.market.Request.ProductReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    //제품관련 정보와 현재 조회한 회원의 고유번호
    public List<PurchaseList> getTransHistory(ProductReq productReq,Long id)
    {
        //판매자인 경우
        if(productReq.getMember().getUserId().equals(id))
        {
            return this.purchaseListRepository.findAllBySellerId(id);
        }else{//구매자인 경우
            return this.purchaseListRepository.findAllByBuyerId(id);
        }
    }
}
