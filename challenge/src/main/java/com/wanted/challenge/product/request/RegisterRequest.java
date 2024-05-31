package com.wanted.challenge.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank String name, @Min(0) int price, @Min(1) int quantity) {
}
