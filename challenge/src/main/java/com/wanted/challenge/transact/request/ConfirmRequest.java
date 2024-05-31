package com.wanted.challenge.transact.request;

import jakarta.validation.constraints.NotNull;

public record ConfirmRequest(@NotNull Long productId) {
}
