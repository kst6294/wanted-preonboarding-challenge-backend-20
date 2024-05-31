package com.wanted.challenge.domain.item.repository;

import com.wanted.challenge.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
