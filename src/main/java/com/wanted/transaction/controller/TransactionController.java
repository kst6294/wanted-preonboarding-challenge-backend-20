package com.wanted.transaction.controller;

import com.wanted.common.dto.ResultDTO;
import com.wanted.transaction.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@Log4j2
@RequiredArgsConstructor
public class TransactionController {

	@PostMapping("/buy")
	public ResponseEntity<ResultDTO<Object>> buy(TransactionDTO transactionDTO) {

		return ResponseEntity.ok(ResultDTO.res(HttpStatus.OK, "상품 구매 성공"));
	}

	@PostMapping("/sellconfrim")
	public ResponseEntity<ResultDTO<Object>> confirm(TransactionDTO transactionDTO) {

		return ResponseEntity.ok(ResultDTO.res(HttpStatus.OK, "상품 확정 성공"));
	}
}
