package com.example.wantedmarketapi.dto.request;


import lombok.Getter;

public class MemberRequestDto {

    @Getter
    public static class SignUpMemberRequest {
        String email;
        String password;
    }

    @Getter
    public static class LoginMemberRequest {
        String email;
        String password;
    }

}
