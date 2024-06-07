package com.wanted.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListDto {

    private List<SellDto> sellDtos = new ArrayList<>();
    private List<PurchaseDto> purchaseDtos = new ArrayList<>();

    public void addSellDtos(List<SellDto> sellDtos) {
        this.sellDtos.addAll(sellDtos);
    }

    public void addPurchaseDtos(List<PurchaseDto> purchaseDtos){
        this.purchaseDtos.addAll(purchaseDtos);
    }
}

