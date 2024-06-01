package com.wanted.challenge.domain.item.dto.response;

import com.wanted.challenge.domain.item.entity.Item;
import com.wanted.challenge.domain.item.entity.SaleStatus;
import com.wanted.challenge.domain.transactionhistory.dto.response.TransactionHistoryResponseDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED
)
public class ItemDetailInfoResponseDTO {

    private Long id;
    private Long userId;
    private String name;
    private Long price;
    private Integer quantity;
    private SaleStatus saleStatus;
    private List<TransactionHistoryResponseDTO> transactionHistoryResponseDTOS;

    @Builder
    public ItemDetailInfoResponseDTO(Long id, Long userId, String name, Long price, Integer quantity, SaleStatus saleStatus, List<TransactionHistoryResponseDTO> transactionHistoryResponseDTOS) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.saleStatus = saleStatus;
        this.transactionHistoryResponseDTOS = transactionHistoryResponseDTOS;
    }

    public static ItemDetailInfoResponseDTO toDTO(Item item, List<TransactionHistoryResponseDTO> transactionHistoryResponseDTOS) {
        return ItemDetailInfoResponseDTO.builder()
                .id(item.getId())
                .userId(item.getMember().getId())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .saleStatus(item.getSaleStatus())
                .transactionHistoryResponseDTOS(transactionHistoryResponseDTOS).build();
    }
}
