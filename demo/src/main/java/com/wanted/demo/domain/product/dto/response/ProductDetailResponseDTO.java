package com.wanted.demo.domain.product.dto.response;

import com.wanted.demo.domain.product.entity.Product;
import com.wanted.demo.domain.product.entity.State;
import com.wanted.demo.domain.statements.dto.response.StatementsHistoryResponseDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private Long price;
    private Long quantity;
    private State state;
    private List<StatementsHistoryResponseDTO> statementsHistoryResponseDTOS;

    @Builder
    public ProductDetailResponseDTO(Long id, Long userId, String name, Long price, Long quantity, State state, List<StatementsHistoryResponseDTO> statementsHistoryResponseDTOS){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.statementsHistoryResponseDTOS = statementsHistoryResponseDTOS;
    }

    public static ProductDetailResponseDTO toDTO(Product product, List<StatementsHistoryResponseDTO> statementsHistoryResponseDTOS){
        return ProductDetailResponseDTO.builder()
                .id(product.getId())
                .userId(product.getUser().getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .state(product.getState())
                .statementsHistoryResponseDTOS(statementsHistoryResponseDTOS)
                .build();
    }





}
