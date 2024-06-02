package com.wanted.wantedpreonboardingchallengebackend20.domain.user.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {
    private String name;
    private String userId;
}
