package com.wanted.demo.domain.product.dto.response;

import com.wanted.demo.domain.product.entity.State;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductResponseDTO {
    private Long id;
    private Long userId;
    private String name;
    private Long price;
    private Long quantity;
    private State state;


    @Builder
    public ProductResponseDTO(Long id, Long userId, String name, Long price, Long quantity, State state){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
    }
}
