package com.wanted.preonboarding.auth.dto;

import com.wanted.preonboarding.auth.validator.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@ValidPassword
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreateAuthToken {

    @NotBlank(message = "email은 필수값입니다.")
    @Email(message = "email 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "password는 필수값입니다.")
    @Length(max = 20, message = "password는 최대 20자 이내입니다.")
    private String password;

}
