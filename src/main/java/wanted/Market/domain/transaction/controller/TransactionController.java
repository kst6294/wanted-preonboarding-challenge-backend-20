package wanted.Market.domain.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import wanted.Market.domain.transaction.entity.Transaction;
import wanted.Market.domain.transaction.serivce.TransactionService;
import wanted.Market.global.auth.CustomMemberDetails;
import wanted.Market.global.common.ResponseDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    //내가 구매한 거래 내역 조회
    @GetMapping("/my")
    public ResponseEntity<ResponseDto<?>> getMyTransaction(@AuthenticationPrincipal CustomMemberDetails customMemberDetails){

        return ResponseEntity.status(200).body(ResponseDto.of("내 거래 조회 성공", transactionService.getMyTransaction(customMemberDetails.getUsername())));
    }
    //구매자 예약 기록
    @GetMapping("/buyer")
    public ResponseEntity<ResponseDto<?>> getBuyerReservationList(@AuthenticationPrincipal CustomMemberDetails customMemberDetails){
        return ResponseEntity.status(200).body(ResponseDto.of("구매자 기준 거래 조회 성공", transactionService.getBuyerTransaction(customMemberDetails.getUsername())));
    }

    //판매자 예약 기록
    @GetMapping("/seller")
    public ResponseEntity<ResponseDto<?>> getSellerReservationList(@AuthenticationPrincipal CustomMemberDetails customMemberDetails){
        return ResponseEntity.status(200).body(ResponseDto.of("판매저 기준 거래 조회 성공", transactionService.getSellerTransaction(customMemberDetails.getUsername())));
    }

    //제품에 대한 거래 내역
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<?>> getTransactionsByProductId(@PathVariable long productId){
        return ResponseEntity.status(200).body(ResponseDto.of("제품의 거래 내역 조회 성공", transactionService.getTransactionsByProductId(productId)));
    }
    @PutMapping("/{transactionId}/approve")
    public ResponseEntity<ResponseDto<?>> approveTransaction(
        @AuthenticationPrincipal CustomMemberDetails customMemberDetails,
        @PathVariable long transactionId){
        return ResponseEntity.status(200).body(ResponseDto.of("제품 승인 성공", transactionService.approveTransaction(customMemberDetails.getUsername(),transactionId)));
    }
}
