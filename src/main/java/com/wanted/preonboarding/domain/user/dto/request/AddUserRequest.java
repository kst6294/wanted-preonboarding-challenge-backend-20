package com.wanted.preonboarding.domain.user.dto.request;

import com.wanted.preonboarding.domain.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddUserRequest {

    @Email(message = "이메일 형식이 틀렸습니다.")
    @Size(max = 320, message = "이메일은 320자 미만으로 입력해주세요.") // 최대길이
    @NotBlank(message = "이메일은 필수 입력사항입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    @Size(min = 8, max = 64, message = "비밀번호는 8~64자 사이로 입력해주세요.") // 비밀번호 길이
    private String password;

    @NotBlank(message = "닉네임은 필수 입력사항입니다.")
    @Size(max = 20)
    private String nickname;

    public User toEntity(String encryptedPassword) {
        return User.builder()
                .email(this.email)
                .password(encryptedPassword)
                .nickname(this.nickname)
                .build();
    }
}
