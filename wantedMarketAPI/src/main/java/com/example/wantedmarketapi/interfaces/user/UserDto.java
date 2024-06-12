package com.example.wantedmarketapi.interfaces.user;

import com.example.wantedmarketapi.domain.user.UserCommand;
import com.example.wantedmarketapi.domain.user.UserInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

public class UserDto {

    @Getter
    public static class RegisterRequest {
        @NotEmpty(message = "email은 필수입력값입니다.")
        private String email;

        @NotEmpty(message = "username은 필수입력값입니다.")
        private String username;

        @NotEmpty(message = "password는 필수입력값입니다.")
        private String password;

        public UserCommand toCommand() {
            return UserCommand.builder()
                    .email(email)
                    .username(username)
                    .password(password)
                    .build();
        }
    }

    @Getter
    public static class RegisterResponse {
        private String username;

        public RegisterResponse(UserInfo userInfo) {
            this.username = userInfo.getUsername();
        }
    }
}
