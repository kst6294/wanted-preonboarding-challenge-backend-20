package com.wanted.repository;

import com.wanted.dto.SellDto;

import java.util.List;

public interface ProductRepositoryCustom {

    List<SellDto> findAllSellDtos();
}
