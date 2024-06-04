package com.wanted.demo.domain.statements.service;

import com.wanted.demo.domain.exception.exception.StatementException;
import com.wanted.demo.domain.exception.exception.UserException;
import com.wanted.demo.domain.exception.responseCode.StatementsExceptionResponseCode;
import com.wanted.demo.domain.exception.responseCode.UserExceptionResponseCode;
import com.wanted.demo.domain.statements.dto.request.PurchaseConfirmRequestDTO;
import com.wanted.demo.domain.statements.dto.response.BuyerReservationResponseDTO;
import com.wanted.demo.domain.statements.dto.response.MyProductStatementsResponseDTO;
import com.wanted.demo.domain.statements.dto.response.SellerReservationResponseDTO;
import com.wanted.demo.domain.statements.entity.Statements;
import com.wanted.demo.domain.statements.repository.StatementsRepository;
import com.wanted.demo.domain.user.entity.User;
import com.wanted.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatementsService {

    private final UserRepository userRepository;
    private final StatementsRepository statementRepository;

    //내가 구매한 용품
    @Transactional(readOnly = true)
    public List<MyProductStatementsResponseDTO> getPurchaseList(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserExceptionResponseCode.EXISTS_USER, id+"의 유저가 존재하지 않습니다."));

        List<MyProductStatementsResponseDTO> list = statementRepository.findStatementsByUser(user);

        return list;
    }

    //구매자 기록(예약)
    @Transactional(readOnly = true)
    public List<BuyerReservationResponseDTO> getBuyerReservationList(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserExceptionResponseCode.EXISTS_USER, id+"의 유저가 존재하지 않습니다."));

        List<BuyerReservationResponseDTO> list = statementRepository.findStatementsByBuyer(user);

        return list;
    }

    //판매자 기록(예약)
    @Transactional(readOnly = true)
    public List<SellerReservationResponseDTO> getSellerReservationList(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserException(UserExceptionResponseCode.EXISTS_USER, id+"의 유저가 존재하지 않습니다."));

        List<SellerReservationResponseDTO> list = statementRepository.findStatementsBySeller(user);

        return list;
    }


    //판매 승인
    @Transactional
    public void sellConfirm(Long userId, Long id){
        //유저(판매자)
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(UserExceptionResponseCode.EXISTS_USER, userId+"의 유저가 존재하지 않습니다."));

        log.info("응1");
        //4번 내역
        Statements statement = statementRepository.findByStatements(id).orElseThrow(() -> new StatementException(StatementsExceptionResponseCode.STATEMENTS_NOT_FOUND, "기록이 존재하지 않습니다."));

        log.info("응2");
        //판매자와 유저가 같지 않으면 안됌
        if(statement.getProduct().getUser()!=user){
            throw new StatementException(StatementsExceptionResponseCode.NOT_MATCH_BUYER,"현재 유저는 판매자가 아닙니다.");
        }

        //이미 판매확정한 상품 체크
        if(statement.isSellStatus()){
            throw new StatementException(StatementsExceptionResponseCode.ALREADY_SALE_PRODUCT_CONFIRM,"이미 판매 확정된 상품입니다.");
        }

        statement.setSellStatus(true);

        statementRepository.save(statement);
    }
}
