package com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class UserResponseDto {
    private Long id;
    private String userId;
    private String userName;
}
