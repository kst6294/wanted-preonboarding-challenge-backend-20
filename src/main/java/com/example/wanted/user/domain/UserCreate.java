package com.example.wanted.user.domain;

import com.example.wanted.user.infrastucture.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {
    @NotNull(message = "아이디가 비어있습니다.")
    @Schema(description = "사용자 아이디", nullable = true, example = "moonjin39418")
    private final String account;
    @NotNull(message = "패스워드가가 비어있습니다.")
    @Schema(description = "패스워드", nullable = true, example = "test1234")
    private final String password;
    @NotNull(message = "이름이 비워있습니다")
    @Schema(description = "유저 이름", nullable = true, example = "홍길동")
    private final String name;

    @Builder
    public UserCreate(
            @JsonProperty("account") String account,
            @JsonProperty("password") String password,
            @JsonProperty("name") String name) {
        this.account = account;
        this.password = password;
        this.name = name;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .account(this.account)
                .password(this.password)
                .name(this.name)
                .role(Role.USER)
                .build();

    }
}
