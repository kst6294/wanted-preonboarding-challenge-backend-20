package market.market.domain.user.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    @Schema(description = "사용자 아이디", nullable = false, example = "beargame")
    private String accountId;

    @Schema(description = "사용자 비밀번호", nullable = false, example = "pwd")
    private String password;
}
