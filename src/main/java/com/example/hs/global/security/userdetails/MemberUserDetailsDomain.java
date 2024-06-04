package com.example.hs.global.security.userdetails;

import com.example.hs.domain.auth.entity.Member;
import com.example.hs.domain.auth.type.Authority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberUserDetailsDomain (
    @NotNull
    long id,

    @NotBlank
    String loginId,

    @NotBlank
    String password,

    @NotNull
    Authority authority
    ){
  public static MemberUserDetailsDomain fromEntity(Member member) {
    return new MemberUserDetailsDomain(
        member.getId(),
        member.getLoginId(),
        member.getPassword(),
        member.getAuthority()
    );
  }
}
