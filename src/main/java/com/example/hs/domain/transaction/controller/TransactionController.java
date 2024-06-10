package com.example.hs.domain.transaction.controller;

import com.example.hs.domain.goods.dto.GoodsDto;
import com.example.hs.domain.goods.dto.GoodsRequest;
import com.example.hs.domain.transaction.dto.TransactionDto;
import com.example.hs.domain.transaction.dto.TransactionRequest;
import com.example.hs.domain.transaction.service.TransactionService;
import com.example.hs.global.security.userdetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
  private final TransactionService transactionService;

  @PostMapping()
  public ResponseEntity<TransactionDto> reserveTransactions(@Valid @RequestBody TransactionRequest transactionRequest,
      @AuthenticationPrincipal CustomUserDetails member) {
    return ResponseEntity.ok(transactionService.reserveTransactions(transactionRequest, member));
  }

}
