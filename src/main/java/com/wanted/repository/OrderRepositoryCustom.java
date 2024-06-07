package com.wanted.repository;

import com.wanted.dto.PurchaseDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<PurchaseDto> findAllPurchaseDtos();
}
