package com.market.wanted.item;

import lombok.Data;

@Data
public class ItemDetailResponse {
    private Long id;
    private String name;
    private Long price;
    private Long count;
    private ItemStatus status;

    public ItemDetailResponse(Item item){
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.count = item.getSaleCount();
        setStatus(item.getSaleCount(), item.getReservationCount());
    }

    private void setStatus(Long count, Long reservationCount) {
        if(count > 0){
            status = ItemStatus.SALE;
        }else if(reservationCount > 0){
            status = ItemStatus.RESERVATION;
        }else{
            status = ItemStatus.COMPLETE;
        }
    }
}
