package com.want.BEProject.goods.repository;

import com.want.BEProject.goods.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    List<Goods> findAllBySellerId(long sellerId);
}
