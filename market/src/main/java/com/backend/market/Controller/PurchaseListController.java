package com.backend.market.Controller;

import com.backend.market.DAO.Entity.PurchaseList;
import com.backend.market.Request.ProductReq;
import com.backend.market.Service.purchaseList.PurchaseListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/history")
public class PurchaseListController {
    private PurchaseListService purchaseListService;

    @PostMapping("/add/{buy_id}")
    public ResponseEntity<?> createPurchaseList(@PathVariable Long buy_id, @RequestBody ProductReq productReq){

        if(Objects.equals(productReq.getMember().getUserId(), buy_id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"구입 권한이 없습니다.");

        purchaseListService.addPurchasList(productReq,buy_id);

        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("list/{member_id}")
    public ResponseEntity<?> getPurchaseList(@PathVariable Long member_id, @RequestBody ProductReq productReq)
    {
        List<PurchaseList> lists = this.purchaseListService.getTransHistory(productReq,member_id);

        if(lists.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"요청한 내용의 결과값이 없습니다.");
        }

        return ResponseEntity.status(200).body(lists);
    }
}
