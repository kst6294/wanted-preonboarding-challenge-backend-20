package com.wanted.challenge.domain.transactionhistory.controller;

import com.wanted.challenge.domain.transactionhistory.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TransactionHistoryController {

    private final TransactionHistoryService transactionHistoryService;
}
