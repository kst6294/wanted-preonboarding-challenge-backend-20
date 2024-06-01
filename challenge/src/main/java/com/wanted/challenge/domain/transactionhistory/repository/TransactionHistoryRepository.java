package com.wanted.challenge.domain.transactionhistory.repository;

import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.domain.transactionhistory.dto.response.MyTransactionHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.dto.response.TransactionHistoryResponseDTO;
import com.wanted.challenge.domain.transactionhistory.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
    @Query("select new com.wanted.challenge.domain.transactionhistory.dto.response.MyTransactionHistoryResponseDTO" +
            "(t.id, i.name, t.purchasePrice, t.saleConfirmStatus, t.purchaseConfirmStatus)" +
            "from TransactionHistory t join t.item i where t.member = :member")
    List<MyTransactionHistoryResponseDTO> findTransactionHistoriesByMember(@Param("member") Member member);
}
