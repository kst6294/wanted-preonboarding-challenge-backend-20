package com.example.hs.domain.goods.repository;

import com.example.hs.domain.goods.entity.Goods;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {
  List<Goods> findAll();
}
