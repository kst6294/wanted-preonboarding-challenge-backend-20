package com.wanted.market.member.dto;

import com.wanted.market.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {    //비밀번호 포함하지 않는 응답 dto
    private Integer memberId;
    private String email;
    private String memberName;
    private String memberRole;

    public static MemberResponseDto createFromEntity(Member member){
        if (member == null) {
            return null;
        }

        return MemberResponseDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .memberName(member.getName())
                .memberRole(member.getRole().toString())
                .build();
    }

}
