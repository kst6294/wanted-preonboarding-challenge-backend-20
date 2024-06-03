package org.example.preonboarding.member.model.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.preonboarding.member.model.enums.Role;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

    @NotNull(message = "이름 입력은 필수 입니다.")
    @Size(min = 2, message = "이름은 최소 2글자 이상입니다.")
    private String userId;

    @NotNull(message = "이름 입력은 필수 입니다.")
    @Size(min = 2, message = "이름은 최소 2글자 이상입니다.")
    private String name;

    @NotNull(message = "비밀번호 입력은 필수 입니다.")
    @Size(min = 2, message = "비밀번호는 최소 2글자 이상입니다.")
    private String password;

    private Role role = Role.MEMBER;
}
