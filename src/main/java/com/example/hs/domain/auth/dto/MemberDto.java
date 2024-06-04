package com.example.hs.domain.auth.dto;

import com.example.hs.domain.auth.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
  private String loginId;
  private String name;
  private String message;

  public static MemberDto fromEntity(Member member, String message) {
    return MemberDto.builder()
        .loginId(member.getLoginId())
        .name(member.getName())
        .message(message)
        .build();
  }
}
