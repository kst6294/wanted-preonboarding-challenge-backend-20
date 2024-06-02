package com.example.wanted.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreate {
    @NotNull(message = "제품 아이디가 입력되지 않았습니다")
    @Schema(description = "제품 ID", nullable = false, example = "1")
    long productId;

    @Builder
    public OrderCreate(
            @JsonProperty("productId") long productId) {
        this.productId = productId;
    }
}
