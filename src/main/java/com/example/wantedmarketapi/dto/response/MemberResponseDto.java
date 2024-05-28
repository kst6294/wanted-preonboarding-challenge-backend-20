package com.example.wantedmarketapi.dto.response;


import lombok.*;

public class MemberResponseDto {

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpMemberResponse {
        Long memberId;
        String email;
    }
}

