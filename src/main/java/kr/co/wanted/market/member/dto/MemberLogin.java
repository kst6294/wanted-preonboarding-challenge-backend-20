package kr.co.wanted.market.member.dto;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import static kr.co.wanted.market.common.global.constants.Constant.*;

public record MemberLogin() {

    public record Request(@NotEmpty(message = "ID 값은 필수입니다.")
                          @Length(min = MEMBER_ID_LENGTH_MIN, max = MEMBER_ID_LENGTH_MAX)
                          String id,

                          @NotEmpty(message = "비밀번호는 필수입니다.")
                          @Length(min = MEMBER_PASSWORD_LENGTH_MIN, max = MEMBER_PASSWORD_LENGTH_MAX)
                          String password) {

    }

    public record Response(String accessToken) {
    }


}
