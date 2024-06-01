package com.wanted.challenge.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailRequestDTO {

    @Email(message = "이메일을 입력해주세요.")
    @Size(max = 50, message = "이메일은 50자까지 가능합니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

}
