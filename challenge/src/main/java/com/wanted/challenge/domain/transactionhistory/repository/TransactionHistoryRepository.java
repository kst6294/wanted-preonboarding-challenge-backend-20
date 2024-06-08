package com.wanted.challenge.domain.transactionhistory.repository;

import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyBuyerReservationHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyPurchaseHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.MySellerReservationHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.TransactionHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.entity.TransactionHistory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    boolean existsByMemberAndItem(Member member, Item item);

    // 멤버, 아이템이 일치하는 거래기록 가져오기
    @Query("select new com.wanted.challenge.domain.transactionhistory.dto.response.TransactionHistoryResponseDTO" +
            "(t.id, t.purchasePrice, t.saleConfirmStatus, t.purchaseConfirmStatus, m.email)" +
            "from TransactionHistory t join t.member m where t.item = :item and t.member = :member")
    List<TransactionHistoryResponseDTO> findTransactionHistoriesList(@Param("member") Member member, @Param("item") Item item);

    // 아이템이 일치하는 거래기록 가져오기
    @Query("select new com.wanted.challenge.domain.transactionhistory.dto.response.TransactionHistoryResponseDTO" +
            "(t.id, t.purchasePrice, t.saleConfirmStatus, t.purchaseConfirmStatus, m.email)" +
            "from TransactionHistory t join t.member m where t.item = :item")
    List<TransactionHistoryResponseDTO> findTransactionHistoriesListByItem(@Param("item") Item item);

    // 현재 유저의 구매기록 가져오기
    @Query("select new com.wanted.challenge.domain.transactionhistory.dto.response.MyPurchaseHistoryResponseDTO" +
            "(t.id, i.name, t.purchasePrice, t.purchaseConfirmDate)" +
            "from TransactionHistory t join t.item i where t.member = :member and t.saleConfirmStatus = true and t.purchaseConfirmStatus = true")
    List<MyPurchaseHistoryResponseDTO> findTransactionHistoriesByMember(@Param("member") Member member);

    // 현재 유저의 예약기록 가져오기(구매자)
    @Query("select new com.wanted.challenge.domain.transactionhistory.dto.response.MyBuyerReservationHistoryResponseDTO" +
            "(t.id, i.name, t.purchasePrice, t.createdDate, t.saleConfirmStatus, t.purchaseConfirmStatus, i.id)" +
            "from TransactionHistory t join t.item i where t.member = :member and t.purchaseConfirmDate is null")
    List<MyBuyerReservationHistoryResponseDTO> findBuyerReservationHistoriesByMember(@Param("member") Member member);

    // 현재 유저의 예약기록 가져오기(판매자)
    @Query("select new com.wanted.challenge.domain.transactionhistory.dto.response.MySellerReservationHistoryResponseDTO" +
            "(t.id, i.name, t.purchasePrice, t.createdDate, t.saleConfirmStatus, t.purchaseConfirmStatus, t.member.email, i.id)" +
            "from TransactionHistory t join t.item i join t.member m where i.member = :member and t.purchaseConfirmDate is null ")
    List<MySellerReservationHistoryResponseDTO> findSellerReservationHistoriesByMember(@Param("member") Member member);

    // 거래기록 가져오기
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from TransactionHistory t join fetch t.item join fetch t.item.member where t.id = :id")
    Optional<TransactionHistory> findByTransactionHistoryFetchJoinItem(@Param("id") Long id);

    // 하나라도 구매승인이 안 된 것이 있는지 확인
    boolean existsByItemAndPurchaseConfirmStatusFalseAndSaleConfirmStatusFalse(@Param("item") Item item);
}
