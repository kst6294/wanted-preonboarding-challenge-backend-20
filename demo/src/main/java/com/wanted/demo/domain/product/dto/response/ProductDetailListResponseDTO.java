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
public class ProductDetailListResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private Long price;
    private Long quantity;
    private State state;
    private List<StatementsHistoryResponseDTO> statementHistoryResponseList;

    @Builder
    public ProductDetailListResponseDTO(Long id, Long userId, String name, Long price, Long quantity, State state, List<StatementsHistoryResponseDTO> statementHistoryResponseList){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.statementHistoryResponseList = statementHistoryResponseList;
    }

    public static ProductDetailListResponseDTO toDTO(Product product, List<StatementsHistoryResponseDTO> statementHistoryResponseList){
        return ProductDetailListResponseDTO.builder()
                .id(product.getId())
                .userId(product.getUser().getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .state(product.getState())
                .statementHistoryResponseList(statementHistoryResponseList).build();
    }


}
