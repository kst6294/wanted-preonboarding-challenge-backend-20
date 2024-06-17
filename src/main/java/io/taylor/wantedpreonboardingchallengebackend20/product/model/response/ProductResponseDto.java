package io.taylor.wantedpreonboardingchallengebackend20.product.model.response;

import java.sql.Timestamp;

public record ProductResponseDto(Long id, String name, long price, int status, Timestamp updatedAt, Timestamp createdAt) {
}
