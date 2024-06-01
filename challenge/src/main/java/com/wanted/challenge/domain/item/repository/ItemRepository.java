package com.wanted.challenge.domain.item.repository;

import com.wanted.challenge.domain.item.dto.response.ItemResponseDTO;
import com.wanted.challenge.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select new com.wanted.challenge.domain.item.dto.response.ItemResponseDTO(i.id, i.member.id, i.name, i.price, i.saleStatus)" +
            "from Item i")
    List<ItemResponseDTO> findAllItems();

    @Query("select i from Item i join fetch i.member where i.id = :id")
    Optional<Item> findByIdFetchJoinMember(@Param("id")Long id);
}
