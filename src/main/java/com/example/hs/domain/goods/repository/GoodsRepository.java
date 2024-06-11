package com.example.hs.domain.goods.repository;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.goods.entity.Goods;
import com.example.hs.domain.goods.type.GoodsStatus;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
  List<Goods> findAll();
  @Lock(LockModeType.PESSIMISTIC_READ)
  @Query("SELECT g FROM Goods g WHERE g.id = :id")
  Optional<Goods> findByIdWithLock(@Param("id") Long id);

  List<Goods> findAllBySeller(Member seller);
  List<Goods> findAllBySellerAndGoodsStatus(Member seller, GoodsStatus goodsStatus);
}
