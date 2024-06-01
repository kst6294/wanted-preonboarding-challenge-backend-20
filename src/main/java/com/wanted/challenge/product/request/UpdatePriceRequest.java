package com.wanted.challenge.product.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdatePriceRequest(@NotNull Long productId, @Min(0) int price) {
}
