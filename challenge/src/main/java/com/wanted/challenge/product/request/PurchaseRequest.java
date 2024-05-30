package com.wanted.challenge.product.request;

import jakarta.validation.constraints.NotNull;

public record PurchaseRequest(@NotNull Long productId) {
}
