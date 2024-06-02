package com.wanted.wantedpreonboardingchallengebackend20.domain.product.dto.response;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegistProductResponseDto {
    private String name;
    private int price;
    private int quantity;
    private UserResponseDto user;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UserResponseDto{
        private String userId;
    }

}
