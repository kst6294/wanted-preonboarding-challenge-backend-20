package com.wantedmarket.Item.repository;

import com.wantedmarket.Item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
