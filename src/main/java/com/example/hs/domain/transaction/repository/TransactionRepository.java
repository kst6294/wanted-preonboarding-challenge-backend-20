package com.example.hs.domain.transaction.repository;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.transaction.entity.Transaction;
import com.example.hs.domain.transaction.type.TransactionStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  boolean existsByGoodsAndBuyer(Goods goods, Member buyer);

  List<Transaction> findAllByGoods(Goods goods);
  List<Transaction> findAllByGoodsAndTransactionStatus(Goods goods, TransactionStatus transactionStatus);

  Optional<Transaction> findByGoodsAndBuyer(Goods goods, Member buyer);

  List<Transaction> findAllByBuyer(Member buyer);
  List<Transaction> findAllByBuyerAndTransactionStatus(Member buyer, TransactionStatus transactionStatus);
}
