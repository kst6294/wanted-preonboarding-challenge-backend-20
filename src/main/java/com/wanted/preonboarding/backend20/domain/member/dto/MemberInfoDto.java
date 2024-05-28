package com.wanted.preonboarding.backend20.domain.member.dto;

import com.wanted.preonboarding.backend20.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoDto {
    private Long id;
    private String name;

    public static MemberInfoDto toMemberDto(Member member) {
        return MemberInfoDto.builder()
                .id(member.getId())
                .name(member.getName())
                .build();
    }
}
