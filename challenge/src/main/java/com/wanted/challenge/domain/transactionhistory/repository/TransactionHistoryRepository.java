package com.wanted.challenge.domain.transactionhistory.repository;

import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.member.entity.Member;
import com.wanted.challenge.domain.transactionhistory.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    boolean existsByMemberAndItem(Member member, Item item);
}
