package market.market.domain.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class SignUpRequest {

    @Schema(description = "사용자 이메일", nullable = false, example = "example@gmail.com")
    private String email;

    @Length(min = 2, max = 15)
    @Schema(description = "사용자 아이디", nullable = false, example = "beargame")
    private String accountId;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Schema(description = "사용자 비밀번호", nullable = false, example = "pwd")
    private String password;

    @Schema(description = "사용자 비밀번호 확인", nullable = false, example = "pwd")
    private String passwordValid;
}
