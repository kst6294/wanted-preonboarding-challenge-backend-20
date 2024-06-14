package org.example.wantedmarket.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinRequest {

    @NotBlank(message = "이름이나 닉네임을 입력해주세요.")
    @Size(min=1, max=20, message = "최소 1자, 최대 20자로 생성하세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min=4, max=15, message = "최소 4자, 최대 15자로 생성하세요.")
    // todo : 디테일하게 정규표현식 정의하기
    private String password;

}
