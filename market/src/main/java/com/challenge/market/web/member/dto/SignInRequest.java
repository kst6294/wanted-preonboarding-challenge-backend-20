package com.challenge.market.web.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 로그인 요청
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SignInRequest {

    /**
     * NAME 을 회원의 로그인 ID로 생각할 것. PK 아님.
     */
    @NotEmpty
    private String name;

    @NotEmpty
    private String pw;

}
