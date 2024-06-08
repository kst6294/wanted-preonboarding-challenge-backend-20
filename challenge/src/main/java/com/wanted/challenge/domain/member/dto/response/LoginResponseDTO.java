package com.wanted.challenge.domain.member.dto.response;

import com.wanted.challenge.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDTO {

    private String email;

    @Builder
    public LoginResponseDTO(String email) {
        this.email = email;
    }

    public static LoginResponseDTO toDTO(Member member){
        return LoginResponseDTO.builder()
                .email(member.getEmail()).build();
    }
}
