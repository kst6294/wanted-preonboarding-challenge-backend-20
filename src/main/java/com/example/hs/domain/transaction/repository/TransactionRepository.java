package com.example.hs.domain.transaction.repository;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  boolean existsByGoodsAndBuyer(Goods goods, Member buyer);
}
