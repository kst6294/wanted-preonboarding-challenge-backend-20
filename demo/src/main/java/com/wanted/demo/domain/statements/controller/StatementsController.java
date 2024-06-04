package com.wanted.demo.domain.statements.controller;

import com.wanted.demo.domain.statements.dto.request.PurchaseConfirmRequestDTO;
import com.wanted.demo.domain.statements.dto.response.BuyerReservationResponseDTO;
import com.wanted.demo.domain.statements.dto.response.MyProductStatementsResponseDTO;
import com.wanted.demo.domain.statements.dto.response.SellerReservationResponseDTO;
import com.wanted.demo.domain.statements.service.StatementsService;
import com.wanted.demo.global.auth.AuthUser;
import com.wanted.demo.global.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/statements")
public class StatementsController {
    private final StatementsService statementService;

    //내가 구매한 용품
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<?>> getPurchaseList(@AuthUser Long useId){
        List<MyProductStatementsResponseDTO> myProductStatementsResponseDTOS = statementService.getPurchaseList(useId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(myProductStatementsResponseDTOS,"내가 구매한 정보 가져오기 성공."));
    }

    //구매자 예약 기록
    @GetMapping("/buyer")
    public ResponseEntity<ApiResponse<?>> getBuyerReservationList(@AuthUser Long useId){
        List<BuyerReservationResponseDTO> buyerReservationResponseDTOS = statementService.getBuyerReservationList(useId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(buyerReservationResponseDTOS,"구매자 예약 정보 가져오기 성공."));
    }

    //판매자 예약 기록
    @GetMapping("/seller")
    public ResponseEntity<ApiResponse<?>> getSellerReservationList(@AuthUser Long userId){
        List<SellerReservationResponseDTO> sellerReservationResponseDTOS = statementService.getSellerReservationList(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(sellerReservationResponseDTOS,"판매자 예약 정보 가져오기 성공."));
    }

    //판매승인
    @PatchMapping("/products/{id}/approve-sale")
    public ResponseEntity<ApiResponse<?>> approveSale(@AuthUser Long userId, @PathVariable(name = "id")Long id){

        statementService.sellConfirm(userId,id);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccessNoContent("판매 승인 성공."));
    }
}
