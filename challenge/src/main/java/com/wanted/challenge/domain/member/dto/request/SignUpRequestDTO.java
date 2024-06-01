package com.wanted.challenge.domain.member.dto.request;

import com.wanted.challenge.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDTO {

    @Email(message = "이메일 형식이 아닙니다.")
    @Size(max = 50, message = "이메일은 50자까지 가능합니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자까지 가능합니다.")
    private String password;

    public SignUpRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member toEntity(String passwordEncoding){
        return Member.builder()
                .email(email)
                .password(passwordEncoding)
                .build();
    }
}
