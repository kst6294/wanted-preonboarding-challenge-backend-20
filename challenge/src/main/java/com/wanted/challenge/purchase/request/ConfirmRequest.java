package com.wanted.challenge.purchase.request;

import jakarta.validation.constraints.NotNull;

public record ConfirmRequest(@NotNull Long productId) {
}
