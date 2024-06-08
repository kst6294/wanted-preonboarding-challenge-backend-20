package com.wanted.challenge.domain.transactionhistory.service.impl;

import com.wanted.challenge.domain.exception.exception.MemberException;
import com.wanted.challenge.domain.exception.exception.TransactionHistoryException;
import com.wanted.challenge.domain.exception.info.MemberExceptionInfo;
import com.wanted.challenge.domain.exception.info.TransactionHistoryExceptionInfo;
import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.domain.member.repository.MemberRepository;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyBuyerReservationHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyPurchaseHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MySellerReservationHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.entity.TransactionHistory;
import com.wanted.challenge.domain.transactionhistory.repository.TransactionHistoryRepository;
import com.wanted.challenge.domain.transactionhistory.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private final MemberRepository memberRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    // 내 구매기록 전부 가져오기
    @Override
    @Transactional(readOnly = true)
    public List<MyPurchaseHistoryResponseDTO> getPurchaseHistories(Long id) {
        Member currentMember = getCurrentMember(id);

        List<MyPurchaseHistoryResponseDTO> transactionHistoriesByMember = transactionHistoryRepository.findTransactionHistoriesByMember(currentMember);

        return transactionHistoriesByMember;
    }

    // 내 예약기록 전부 가져오기(구매자)
    @Override
    @Transactional(readOnly = true)
    public List<MyBuyerReservationHistoryResponseDTO> getBuyerReservationHistories(Long id) {
        Member currentMember = getCurrentMember(id);

        List<MyBuyerReservationHistoryResponseDTO> reservationHistoriesByMember = transactionHistoryRepository.findBuyerReservationHistoriesByMember(currentMember);

        return reservationHistoriesByMember;
    }

    // 내 예약기록 전부 가져오기(판매자)
    @Override
    @Transactional(readOnly = true)
    public List<MySellerReservationHistoryResponseDTO> getSellerReservationHistories(Long id) {
        Member currentMember = getCurrentMember(id);

        List<MySellerReservationHistoryResponseDTO> sellerReservationHistoriesByMember = transactionHistoryRepository.findSellerReservationHistoriesByMember(currentMember);

        return sellerReservationHistoriesByMember;
    }

    // 판매 승인
    @Override
    @Transactional
    public void saleConfirm(Long userId, Long historyId) {
        Member currentMember = getCurrentMember(userId);

        TransactionHistory transactionHistory = transactionHistoryRepository.findByTransactionHistoryFetchJoinItem(historyId)
                .orElseThrow(() -> new TransactionHistoryException(TransactionHistoryExceptionInfo.NOT_FOUNT_HISTORY,
                        userId + "번 유저가 " + historyId + "번 거래기록 판매승인 시도(거래기록 없음)"));

        // 아이템 판매자가 맞는지 확인
        if (transactionHistory.getItem().getMember() != currentMember){
            throw new TransactionHistoryException(TransactionHistoryExceptionInfo.NOT_MATCH_ITEM_SELLER,
                    userId + "번 유저가 " + transactionHistory.getId() + "번 기록 판매승인 시도(판매자 불일치)");
        }

        // 이미 승인된 경우
        if (transactionHistory.isSaleConfirmStatus()){
            throw new TransactionHistoryException(TransactionHistoryExceptionInfo.ALREADY_SALE_CONFIRM,
                    userId + "번 유저가 " + transactionHistory.getId() + "번 기록 판매승인 시도(중복 승인)");
        }

        // 판매 승인
        transactionHistory.confirmSale();
    }

    // 구매 확인
    @Override
    @Transactional
    public void purchaseConfirm(Long userId, Long historyId) {
        Member currentMember = getCurrentMember(userId);

        TransactionHistory transactionHistory = transactionHistoryRepository.findByTransactionHistoryFetchJoinItem(historyId)
                .orElseThrow(() -> new TransactionHistoryException(TransactionHistoryExceptionInfo.NOT_FOUNT_HISTORY,
                        userId + "번 유저가 " + historyId + "번 거래기록 구매확인 시도(거래기록 없음)"));

        // 아이템 구매자가 맞는지 확인
        if (transactionHistory.getMember() != currentMember){
            throw new TransactionHistoryException(TransactionHistoryExceptionInfo.NOT_MATCH_ITEM_BUYER,
                    userId + "번 유저가 " + transactionHistory.getId() + "번 기록 구매확인 시도(구매자 불일치)");
        }

        // 판매 확정이 안 된 경우
        if (!transactionHistory.isSaleConfirmStatus()){
            throw new TransactionHistoryException(TransactionHistoryExceptionInfo.NOT_SALE_CONFIRM,
                    userId + "번 유저가 " + transactionHistory.getId() + "번 기록 구매확인 시도(판매확정 미진행)");
        }

        // 이미 구매된 경우
        if (transactionHistory.isPurchaseConfirmStatus()){
            throw new TransactionHistoryException(TransactionHistoryExceptionInfo.ALREADY_PURCHASE_CONFIRM,
                    userId + "번 유저가 " + transactionHistory.getId() + "번 기록 구매확인 시도(중복 승인)");
        }

        // 구매 확인
        transactionHistory.confirmPurchase();
        // 재고가 없고 모두 구매확인이 끝났다면
        if (transactionHistory.getItem().getQuantity() <= 0 && !transactionHistoryRepository.existsByItemAndPurchaseConfirmStatusFalseAndSaleConfirmStatusFalse(transactionHistory.getItem())){
            transactionHistory.getItem().changeSaleStatusSoldOut();
        }
    }


    private Member getCurrentMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberExceptionInfo.NOT_FOUNT_MEMBER, id + "번 유저가 존재하지 않습니다."));
    }
}
