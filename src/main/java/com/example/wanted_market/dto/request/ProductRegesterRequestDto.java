package com.example.wanted_market.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record ProductRegesterRequestDto(
        @NotBlank @JsonProperty("name") String name,
        @NotBlank @JsonProperty("price") int price
) {
}
