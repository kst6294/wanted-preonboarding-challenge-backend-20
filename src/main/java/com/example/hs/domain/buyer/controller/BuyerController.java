package com.example.hs.domain.buyer.controller;

import com.example.hs.domain.buyer.service.BuyerService;
import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.transaction.type.TransactionStatus;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buyers")
public class BuyerController {
  private final BuyerService buyerService;

  @GetMapping("/goods}")
  public ResponseEntity<List<GoodsDto>> getAllMyTransactionGoods(@AuthenticationPrincipal CustomUserDetails member) {
    return ResponseEntity.ok(buyerService.getAllMyTransactionGoods(member));
  }

  @GetMapping("/goods/status/{transactionStatus}")
  public ResponseEntity<List<GoodsDto>> getAllMyTransactionGoodsByStatus(@AuthenticationPrincipal CustomUserDetails member,
      TransactionStatus transactionStatus) {
    return ResponseEntity.ok(buyerService.getAllMyTransactionGoodsByStatus(member, transactionStatus));
  }
}
