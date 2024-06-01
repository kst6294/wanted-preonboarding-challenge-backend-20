package com.wanted.challenge.domain.transactionhistory.service.impl;

import com.wanted.challenge.domain.exception.exception.MemberException;
import com.wanted.challenge.domain.exception.info.MemberExceptionInfo;
import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.domain.member.repository.MemberRepository;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyPurchaseHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyBuyerReservationHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MySellerReservationHistoryResponseDTO;
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


    private Member getCurrentMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberException(MemberExceptionInfo.NOT_FOUNT_MEMBER, id + "번 유저가 존재하지 않습니다."));
    }
}
