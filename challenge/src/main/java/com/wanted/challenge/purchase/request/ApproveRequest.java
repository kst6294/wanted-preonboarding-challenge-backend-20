package com.wanted.challenge.purchase.request;

import jakarta.validation.constraints.NotNull;

public record ApproveRequest(@NotNull Long productId, @NotNull Long buyerId) {
}
