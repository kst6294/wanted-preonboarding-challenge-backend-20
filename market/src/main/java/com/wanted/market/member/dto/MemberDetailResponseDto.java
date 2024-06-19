package com.wanted.market.member.dto;


import com.wanted.market.member.domain.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailResponseDto {

    private Integer id;

    private String email;

    private String name;

    private String password;

    private String role;

    public static MemberDetailResponseDto createFromEntity(Member member) {
        return MemberDetailResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .password(member.getPassword())
                .role(member.getRole().toString())
                .build();
    }
}
