package com.wanted.demo.domain.product.dto.response;

import com.wanted.demo.domain.product.entity.Product;
import com.wanted.demo.domain.product.entity.State;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRegisterResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private Long price;
    private Long quantity;
    private State state;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder
    public ProductRegisterResponseDTO(Long id, Long userId, String name, Long price, Long quantity, State state, LocalDateTime createdTime, LocalDateTime updatedTime){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public static ProductRegisterResponseDTO toDTO(Product product){
        return ProductRegisterResponseDTO.builder()
                .id(product.getId())
                .userId(product.getUser().getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .state(product.getState())
                .createdTime(product.getCreatedDateTime())
                .updatedTime(product.getUpdatedDateTime()).build();
    }
}
