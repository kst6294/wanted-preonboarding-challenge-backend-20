package wanted.market.api.member.domain.dto.`in`

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "회원가입")
data class CommandRegisterMember(

    @Schema(description = "아이디", example = "seonwoo_jung")
    val userId: String,

    @Schema(description = "비밀번호", example = "12345678a")
    val password: String
)
