package com.challenge.market.member.dto;

import com.challenge.market.member.domain.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String pw;




}
