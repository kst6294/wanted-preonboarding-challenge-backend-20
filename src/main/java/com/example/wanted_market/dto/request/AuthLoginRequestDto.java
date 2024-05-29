package com.example.wanted_market.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDto(
        @Email(message = "이메일 형식을 확인해주세요.")
        @NotBlank @JsonProperty("email") String email,
        @NotBlank @JsonProperty("password") String password
) {
}
