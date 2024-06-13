package com.challenge.market.web.member.dto;

import com.challenge.market.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SignUpResponse {

    private Long id;
    private String name;

    public static SignUpResponse of(Member member) {
        return SignUpResponse.builder()
                .name(member.getName())
                .id(member.getId())
                .build();
    }
}
