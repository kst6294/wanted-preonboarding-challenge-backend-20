package com.example.wanted.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLogin {
    @NotNull(message = "아이디가 비어있습니다.")
    @Schema(description = "사용자 아이디", nullable = true, example = "moonjin39418")
    private final String account;
    @NotNull(message = "패스워드가가 비어있습니다.")
    @Schema(description = "패스워드", nullable = true, example = "test1234")
    private final String password;

    @Builder
    public UserLogin(
            @JsonProperty("account") String account,
            @JsonProperty("password") String password
    ) {
        this.account = account;
        this.password = password;
    }
}
